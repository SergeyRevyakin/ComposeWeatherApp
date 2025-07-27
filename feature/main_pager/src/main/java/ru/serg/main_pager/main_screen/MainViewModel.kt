@file:OptIn(FlowPreview::class)

package ru.serg.main_pager.main_screen

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.main_pager.PagerScreenError
import ru.serg.main_pager.PagerScreenState
import ru.serg.main_pager.mvi.MainScreenIntent
import ru.serg.main_pager.use_case.GetCityWeatherUseCase
import ru.serg.main_pager.use_case.GetCurrentLocationUseCase
import ru.serg.main_pager.use_case.GetLocalStoredWeatherUseCase
import ru.serg.main_pager.use_case.GetLocationWeatherUseCase
import ru.serg.main_pager.use_case.IsDarkThemeEnabledUseCase
import ru.serg.main_pager.use_case.IsDateExpiredUseCase
import ru.serg.main_pager.use_case.IsNetworkAvailableUseCase
import ru.serg.main_pager.use_case.RemoveFavouriteCityUseCase
import ru.serg.model.WeatherItem
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalStoredWeatherUseCase: GetLocalStoredWeatherUseCase,
    private val getLocationWeatherUseCase: GetLocationWeatherUseCase,
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
    private val removeFavouriteCityUseCase: RemoveFavouriteCityUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val isDateExpired: IsDateExpiredUseCase,
    isDarkThemeEnabledUseCase: IsDarkThemeEnabledUseCase,
    private val isNetworkAvailableUseCase: IsNetworkAvailableUseCase,
) : ViewModel() {

    private val _pagerScreenState = MutableStateFlow(PagerScreenState.defaultState())
    val pagerScreenState = _pagerScreenState
        .onStart {
            emitIntent(MainScreenIntent.InitScreen)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagerScreenState.defaultState()
        )

    private val intentFlow = MutableSharedFlow<MainScreenIntent>()

    val isDarkThemeEnabled = isDarkThemeEnabledUseCase()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, t ->

            _pagerScreenState.update {
                it.copy(
                    isLoading = false,
                    error = PagerScreenError.NetworkError("Something went wrong", throwable = t)
                )
            }
        }

    init {
        viewModelScope.launch {
            intentFlow.distinctUntilChanged().collect { intent ->
                processIntent(intent)
            }
        }
    }

    private fun checkLocationPermission() {
        val locationPermissionFlow = PermissionFlow.getInstance().getMultiplePermissionState(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        viewModelScope.launch {
            locationPermissionFlow.distinctUntilChangedBy { _pagerScreenState.value.isLocationAvailable == it.grantedPermissions.isNotEmpty() }
                .collectLatest { permissionState ->
                    emitIntent(MainScreenIntent.SetLocationPermission(permissionState.grantedPermissions.isNotEmpty()))
                }
        }
    }

    private fun checkNetworkAvailability() {
        viewModelScope.launch {
            isNetworkAvailableUseCase().distinctUntilChanged()
                .collectLatest { isAvailable ->
                    emitIntent(MainScreenIntent.SetNetworkAvailability(isAvailable))
                }
        }
    }

    fun emitIntent(intent: MainScreenIntent) {
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }

    private fun processIntent(intent: MainScreenIntent) {

        when (intent) {
            MainScreenIntent.InitScreen -> initScreen()

            MainScreenIntent.RefreshScreen -> refresh()

            is MainScreenIntent.SetLocationPermission -> setLocationPermission(intent.isGranted)


            is MainScreenIntent.SetPageNumber -> setPageNumber(intent.pageNumber)


            MainScreenIntent.TurnOffWelcomeDialog -> turnOffDialog()

            is MainScreenIntent.SetNetworkAvailability -> setNetworkAvailability(intent.isAvailable)

        }

    }

    private fun initScreen() {
        checkLocationPermission()
        checkNetworkAvailability()
        initCitiesWeatherFlow()
        setInitialState()
    }

    private fun initCitiesWeatherFlow() {
        viewModelScope.launch {
            getLocalStoredWeatherUseCase().distinctUntilChanged().collectLatest { items ->
                _pagerScreenState.update {
                    it.copy(
                        isLoading = false,
                        isStartUp = false,
                        weatherList = items,
                        error = null,
                        hasWelcomeDialog = items.isEmpty() && !it.isLocationAvailable
                    )
                }
            }
        }
    }

    private fun setLocationPermission(isGranted: Boolean) {
        _pagerScreenState.update {
            it.copy(
                isLocationAvailable = isGranted,
            )
        }
    }

    private fun setNetworkAvailability(isAvailable: Boolean) {
        _pagerScreenState.update {
            it.copy(
                isNetworkAvailable = isAvailable,
                error = if (isAvailable) null else it.error
            )
        }
    }

    private fun setPageNumber(pageNumber: Int) {
        _pagerScreenState.update {
            it.copy(
                activeItem = pageNumber
            )
        }
    }

    private fun turnOffDialog() {
        _pagerScreenState.update {
            it.copy(
                hasWelcomeDialog = false
            )
        }
    }

    private fun setInitialState() {
        viewModelScope.launch(coroutineExceptionHandler) {

            _pagerScreenState.debounce(200L).distinctUntilChanged().collectLatest { state ->
                when {
                    state.isLoading || state.error != null -> return@collectLatest

                    state.weatherList.isEmpty() -> {
                        when {
                            state.isLocationAvailable -> checkLocationAndFetchWeather()
                            else -> _pagerScreenState.update {
                                it.copy(
                                    isInit = true
                                )
                            }
                        }
                    }

                    else -> {
                        try {
                            val item = state.weatherList[state.activeItem]
                            checkWeatherItem(item)
                        } catch (_: Exception) {
                            _pagerScreenState.update {
                                it.copy(
                                    activeItem = it.activeItem - 1
                                )
                            }
                            setInitialState()
                        }
                    }
                }
            }
        }
    }


    private fun checkWeatherItem(weatherItem: WeatherItem) {
        viewModelScope.launch(coroutineExceptionHandler) {
            when {
                _pagerScreenState.value.error is PagerScreenError.NetworkError -> Unit

                !_pagerScreenState.value.isNetworkAvailable -> Unit

                isDateExpired(weatherItem.cityItem.lastTimeUpdated) -> {
                    refresh()
                }

                weatherItem.hourlyWeatherList.isEmpty() || weatherItem.dailyWeatherList.isEmpty() -> {
                    refresh()
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch(coroutineExceptionHandler) {

            val updatedWeatherItem =
                _pagerScreenState.value.weatherList[_pagerScreenState.value.activeItem]

            if (updatedWeatherItem.cityItem.isFavorite) {
                if (_pagerScreenState.value.isLocationAvailable) {
                    checkLocationAndFetchWeather()
                } else removeFavouriteCityUseCase(updatedWeatherItem)
            } else getCityWeatherUseCase(updatedWeatherItem.cityItem)
                .asResult()
                .collectLatest { result ->
                    processNetworkResult(result)
                }
        }
    }

    private fun checkLocationAndFetchWeather() {
        _pagerScreenState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
        viewModelScope.launch(coroutineExceptionHandler) {
            getCurrentLocationUseCase()
                .distinctUntilChanged()
                .catch { e ->
                    if (_pagerScreenState.value.weatherList.isEmpty()) {
                        _pagerScreenState.update {
                            it.copy(
                                isLoading = false,
                                isInit = true,
                                isLocationAvailable = false,
                                error = PagerScreenError.GPSError(
                                    message = e.message.orEmpty()
                                )
                            )
                        }
                    }
                }
                .collectLatest { coordinatesWrapper ->
                    getLocationWeatherUseCase(
                        coordinatesWrapper,
                    ).asResult()
                        .collectLatest { result ->
                            processNetworkResult(result)
                        }
                }
        }
    }

    private fun processNetworkResult(result: NetworkResult<WeatherItem>) {

        when (result) {
            is NetworkResult.Error -> _pagerScreenState.update {
                it.copy(
                    isLoading = false,
                    isInit = true,
                    error = PagerScreenError.NetworkError(
                        result.message.orEmpty(),
                        result.throwable
                    )
                )
            }

            NetworkResult.Loading -> {
                _pagerScreenState.update {
                    it.copy(
                        isLoading = true,
                        error = null
                    )
                }
            }

            is NetworkResult.Success -> {
                //TODO think about
//                val mutableList =
//                    _pagerScreenState.value.weatherList.toMutableList()
//                mutableList[_pagerScreenState.value.activeItem] = result.data
//                _pagerScreenState.update {
//                    it.copy(
//                        isLoading = false,
//                        isInit = true,
//                        weatherList = mutableList,
//                        error = null
//                    )
//                }
            }
        }
    }
}