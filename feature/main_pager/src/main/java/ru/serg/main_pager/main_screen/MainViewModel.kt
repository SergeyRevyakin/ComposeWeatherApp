@file:OptIn(FlowPreview::class)

package ru.serg.main_pager.main_screen

import android.Manifest
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import ru.serg.main_pager.PagerScreenError
import ru.serg.main_pager.PagerScreenState
import ru.serg.main_pager.isExpired
import ru.serg.main_pager.mvi.MainScreenIntent
import ru.serg.main_pager.use_case.GetCurrentLocationUseCase
import ru.serg.main_pager.use_case.GetLocalStoredWeatherUseCase
import ru.serg.main_pager.use_case.GetLocationWeatherUseCase
import ru.serg.main_pager.use_case.IsDarkThemeEnabledUseCase
import ru.serg.main_pager.use_case.IsNetworkAvailableUseCase
import ru.serg.main_pager.use_case.RefreshWeatherUseCase
import ru.serg.mvi_core.CoreViewModel
import ru.serg.mvi_core.models.IEffect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalStoredWeatherUseCase: GetLocalStoredWeatherUseCase,
    private val getLocationWeatherUseCase: GetLocationWeatherUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    isDarkThemeEnabledUseCase: IsDarkThemeEnabledUseCase,
    private val isNetworkAvailableUseCase: IsNetworkAvailableUseCase,
    private val refreshWeatherUseCase: RefreshWeatherUseCase
) : CoreViewModel<PagerScreenState, MainScreenIntent, IEffect>() {

    val isDarkThemeEnabled = isDarkThemeEnabledUseCase()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
        sendAction(
            MainScreenIntent.SetError(PagerScreenError.NetworkError(t))
        )
    }

    private fun checkLocationPermission() {
        val locationPermissionFlow = PermissionFlow.getInstance().getMultiplePermissionState(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        viewModelScope.launch {
            locationPermissionFlow.distinctUntilChangedBy { state.value.isLocationAvailable == it.grantedPermissions.isNotEmpty() }
                .debounce(300L)
                .collectLatest { permissionState ->
                    sendAction(MainScreenIntent.SetLocationPermission(permissionState.grantedPermissions.isNotEmpty()))
                }
        }
    }

    private fun checkNetworkAvailability() {
        viewModelScope.launch {
            isNetworkAvailableUseCase().distinctUntilChanged().collectLatest { isAvailable ->
                sendAction(MainScreenIntent.SetNetworkAvailability(isAvailable))
            }
        }
    }

    override fun getInitialState(): PagerScreenState = PagerScreenState.defaultState()

    override fun processAction(action: MainScreenIntent): PagerScreenState {

        return when (action) {

            MainScreenIntent.RefreshScreen -> refresh()

            is MainScreenIntent.SetLocationPermission -> setLocationPermission(action.isGranted)

            is MainScreenIntent.SetPageNumber -> setPageNumber(action.pageNumber)

            is MainScreenIntent.PushStateToBackStack -> action.state

            is MainScreenIntent.ShowEmptyCitiesScreen -> turnOffDialog(
                action.hasWelcomeDialog,
                action.isLoading
            )

            is MainScreenIntent.SetNetworkAvailability -> setNetworkAvailability(action.isAvailable)

            MainScreenIntent.GetLocationWeather -> checkLocationAndFetchWeather()

            is MainScreenIntent.SetError -> setState {
                this.copy(
                    error = action.error,
                    isLoading = false
                )
            }
        }

    }

    override fun initScreen() {
        checkLocationPermission()
        checkNetworkAvailability()
        initCitiesWeatherFlow()
        checkActiveItem()
    }

    private fun checkActiveItem() {
        viewModelScope.launch(coroutineExceptionHandler) {
            state.distinctUntilChanged { old, new ->
                old.activeItem == new.activeItem && old.weatherList == new.weatherList
                        && old.isLocationAvailable == new.isLocationAvailable
            }.collectLatest { state ->
                state.weatherList.getOrNull(state.activeItem)?.let {
                    if (it.isExpired(0.25) || it.dailyWeatherList.isEmpty() || it.hourlyWeatherList.isEmpty()) sendAction(
                        MainScreenIntent.RefreshScreen
                    )
                }
            }
        }
    }

    private fun initCitiesWeatherFlow() {
        viewModelScope.launch {
            getLocalStoredWeatherUseCase().collectLatest {
                if (it.isEmpty() && !state.value.isLocationAvailable) {
                    sendAction(
                        MainScreenIntent.ShowEmptyCitiesScreen(
                            hasWelcomeDialog = true,
                            isLoading = false
                        )
                    )
                } else sendAction(
                    MainScreenIntent.PushStateToBackStack(
                        setState {
                            this.copy(
                                isLoading = false,
                                weatherList = it,
                                error = null,
                                hasWelcomeDialog = it.isEmpty() && !this.isLocationAvailable
                            )
                        }
                    )
                )
            }
        }
    }

    private fun setLocationPermission(isGranted: Boolean): PagerScreenState {
        return setState {
            this.copy(
                isLocationAvailable = isGranted,
            )
        }.also {
            if (isGranted) sendAction(MainScreenIntent.GetLocationWeather)
        }
    }

    private fun setNetworkAvailability(isAvailable: Boolean): PagerScreenState {
        return setState {
            this.copy(
                isNetworkAvailable = isAvailable, error = if (isAvailable) null else this.error
            )
        }
    }

    private fun setPageNumber(pageNumber: Int): PagerScreenState {
        if (pageNumber > 0 && pageNumber > state.value.weatherList.size - 1) {
            return setPageNumber(pageNumber - 1)
        }
        return setState {
            this.copy(
                activeItem = pageNumber
            )
        }
    }

    private fun turnOffDialog(hasWelcomeDialog: Boolean, isLoading: Boolean): PagerScreenState {
        return setState {
            this.copy(
                hasWelcomeDialog = hasWelcomeDialog,
                isLoading = isLoading
            )
        }
    }

    private fun refresh(): PagerScreenState {
        viewModelScope.launch(coroutineExceptionHandler) {
            state.value.weatherList.getOrNull(state.value.activeItem)?.let {
                if (it.cityItem.isFavorite) sendAction(MainScreenIntent.GetLocationWeather)
                else refreshWeatherUseCase(it.cityItem)
            } ?: if (state.value.isLocationAvailable) {
                sendAction(MainScreenIntent.GetLocationWeather)
            } else {
                setState {
                    this.copy(
                        isLoading = false,
                    )
                }
            }
        }

        return setState {
            this.copy(
                isLoading = true
            )
        }
    }

    private fun checkLocationAndFetchWeather(): PagerScreenState {
        viewModelScope.launch(coroutineExceptionHandler) {
            getCurrentLocationUseCase().collectLatest {
                getLocationWeatherUseCase(it)
            }
        }

        return setState {
            this.copy(
                isLoading = true
            )
        }
    }
}