@file:OptIn(FlowPreview::class)

package ru.serg.composeweatherapp.ui.screens.main_screen

import android.Manifest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import ru.serg.common.NetworkResult
import ru.serg.common.NetworkStatus
import ru.serg.composeweatherapp.ui.screens.CommonScreenState
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.local.LocalDataSource
import ru.serg.model.UpdatedWeatherItem
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: com.serg.weather.WeatherRepository,
    private val locationService: ru.serg.location.LocationDataSource,
    private val dateUtils: DateUtils,
    private val networkStatus: NetworkStatus
) : ViewModel() {

    var isLoading = mutableStateOf(false)

    val observableItemNumber = MutableStateFlow(0)

    private var _citiesWeather: MutableStateFlow<CommonScreenState> =
        MutableStateFlow(CommonScreenState.Loading)

    var citiesWeather = _citiesWeather.asStateFlow()

    init {
        val locationPermissionFlow = PermissionFlow.getInstance().getMultiplePermissionState(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        viewModelScope.launch {
            locationPermissionFlow.collect {
                setInitialState(it.grantedPermissions.isNotEmpty())
            }

        }

        initCitiesWeatherFlow()

    }

    private fun initCitiesWeatherFlow() {
        viewModelScope.launch {
            localDataSource.getWeatherFlow().debounce(200L).collectLatest {
                when {
                    it.isEmpty() -> _citiesWeather.emit(CommonScreenState.Empty)
                    else -> {
                        isLoading.value = false
                        _citiesWeather.emit(CommonScreenState.Success(it))
                    }
                }
            }
        }
    }

    private fun setInitialState(isLocationAvailable: Boolean) {
        viewModelScope.launch {
            citiesWeather.debounce(200L).collectLatest { state ->
                when (state) {
                    is CommonScreenState.Empty -> {
                        if (isLocationAvailable) {
                            checkLocationAndFetchWeather()
                            _citiesWeather.emit(CommonScreenState.Loading)
                        }
                    }

                    is CommonScreenState.Success -> {
                        observableItemNumber.collectLatest {
                            val item =
                                (citiesWeather.value as CommonScreenState.Success).updatedWeatherList[it]
                            checkWeatherItem(item)
                        }
                    }

                    is CommonScreenState.Loading -> {}

                    else -> {}
                }
            }
        }
    }


    private fun checkWeatherItem(weatherItem: UpdatedWeatherItem) {
        viewModelScope.launch {
            when {
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
        if (networkStatus.isNetworkConnected()) {
            isLoading.value = true
            viewModelScope.launch {
                val number = observableItemNumber.value
                val item =
                    (citiesWeather.value as? CommonScreenState.Success)?.updatedWeatherList?.get(
                        number
                    )

                item?.let { updatedWeatherItem ->
                    if (updatedWeatherItem.cityItem.isFavorite) {
                        checkLocationAndFetchWeather()
                    } else weatherRepository.getCityWeatherFlow(updatedWeatherItem.cityItem)
                        .debounce(200L)
                        .collectLatest {
                            when (it) {
                                is NetworkResult.Loading -> isLoading.value = true
                                is NetworkResult.Error -> isLoading.value = false
                                is NetworkResult.Success -> isLoading.value = false
                            }
                        }
                }
            }
        }
    }

    private fun checkLocationAndFetchWeather() {
        viewModelScope.launch {
            locationService.getLocationUpdate()
                .flatMapLatest { coordinatesWrapper ->
                    weatherRepository.fetchCurrentLocationWeather(
                        coordinatesWrapper,
                    )
                }.launchIn(this)
        }
    }
}