@file:OptIn(FlowPreview::class)

package ru.serg.main_pager.main_screen

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.serg.common.NetworkResult
import ru.serg.common.NetworkStatus
import ru.serg.common.asResult
import ru.serg.local.LocalDataSource
import ru.serg.location.LocationService
import ru.serg.main_pager.DateUseCase
import ru.serg.main_pager.PagerScreenError
import ru.serg.main_pager.PagerScreenState
import ru.serg.model.WeatherItem
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationService,
    private val dateUtils: DateUseCase,
    private val networkStatus: NetworkStatus
) : ViewModel() {

    private val _pagerScreenState = MutableStateFlow(PagerScreenState.defaultState())
    val pagerScreenState = _pagerScreenState.asStateFlow()


    val isDarkThemeEnabled = dateUtils.isDarkThemeEnabled()
    private val isLocationAvailable = MutableStateFlow(false)

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
        val locationPermissionFlow = PermissionFlow.getInstance().getMultiplePermissionState(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        viewModelScope.launch {
            _pagerScreenState.collectLatest {
                Log.d("Tag", "------ $it")
            }
        }

        viewModelScope.launch {
            locationPermissionFlow.collectLatest { permissionState ->
                _pagerScreenState.update {
                    it.copy(
                        isLocationAvailable = permissionState.grantedPermissions.isNotEmpty()
                    )
                }
                isLocationAvailable.emit(
                    permissionState.grantedPermissions.isNotEmpty()
                )
                setInitialState(permissionState.grantedPermissions.isNotEmpty())
            }
        }
        initCitiesWeatherFlow()
    }

    fun initCitiesWeatherFlow() {
        viewModelScope.launch {
            localDataSource.getWeatherFlow().distinctUntilChanged().collectLatest { items ->
                _pagerScreenState.update {
                    it.copy(
                        isLoading = false,
                        isStartUp = false,
                        weatherList = items,
                        error = null
                    )
                }
            }
        }
    }

    private fun setInitialState(isLocationAvailable: Boolean) {
        viewModelScope.launch(coroutineExceptionHandler) {

            _pagerScreenState.debounce(200L).distinctUntilChanged().collectLatest { state ->
                when {
                    state.weatherList.isEmpty() && !state.isStartUp -> {
                        if (isLocationAvailable) checkLocationAndFetchWeather()
                    }

                    !state.isLoading -> {
                        try {
                            val item = state.weatherList[state.activeItem]
                            checkWeatherItem(item)
                        } catch (_: Exception) {
                            _pagerScreenState.update {
                                it.copy(
                                    activeItem = it.activeItem - 1
                                )
                            }
                            setInitialState(isLocationAvailable)
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

                !networkStatus.isNetworkConnected() -> Unit

                dateUtils.isFetchDateExpired(weatherItem.cityItem.lastTimeUpdated) -> {
                    refresh()
                }

                weatherItem.hourlyWeatherList.isEmpty() || weatherItem.dailyWeatherList.isEmpty() -> {
                    refresh()
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(coroutineExceptionHandler) {

            val item = _pagerScreenState.value.weatherList[_pagerScreenState.value.activeItem]

            item.let { updatedWeatherItem ->
                if (updatedWeatherItem.cityItem.isFavorite) {
                    if (isLocationAvailable.value) {
                        checkLocationAndFetchWeather()
                    } else weatherRepository.removeFavouriteCityParam(updatedWeatherItem)
                } else weatherRepository.getCityWeatherFlow(updatedWeatherItem.cityItem)
                    .asResult()
                    .collectLatest { result ->
                        when (result) {
                            is NetworkResult.Error -> _pagerScreenState.update {
                                it.copy(
                                    isLoading = false,
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
                                val mutableList =
                                    _pagerScreenState.value.weatherList.toMutableList()
                                mutableList[_pagerScreenState.value.activeItem] = result.data
                                _pagerScreenState.update {
                                    it.copy(
                                        isLoading = true,
                                        weatherList = mutableList,
                                        error = null
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }

    fun setPageNumber(number: Int) {
        _pagerScreenState.update {
            it.copy(
                activeItem = number
            )
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
            locationService.getLocationUpdate()
                .collectLatest { coordinatesWrapper ->
                    weatherRepository.fetchCurrentLocationWeather(
                        coordinatesWrapper,
                    ).asResult()
                        .collectLatest { result ->
                            when (result) {
                                is NetworkResult.Error -> _pagerScreenState.update {
                                    it.copy(
                                        isLoading = false,
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
                                    val mutableList =
                                        _pagerScreenState.value.weatherList.toMutableList()
                                    mutableList[_pagerScreenState.value.activeItem] = result.data
                                    _pagerScreenState.update {
                                        it.copy(
                                            isLoading = true,
                                            weatherList = mutableList,
                                            error = null
                                        )
                                    }
                                }
                            }
                        }
                }
        }
    }
}