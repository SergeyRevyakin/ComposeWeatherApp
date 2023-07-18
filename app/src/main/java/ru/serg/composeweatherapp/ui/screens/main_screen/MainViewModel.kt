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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.WeatherRepository
import ru.serg.composeweatherapp.data.data_source.LocationDataSource
import ru.serg.composeweatherapp.data.data_source.UpdatedLocalDataSource
import ru.serg.composeweatherapp.data.dto.UpdatedWeatherItem
import ru.serg.composeweatherapp.ui.screens.CommonScreenState
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.NetworkStatus
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val updatedLocalDataSource: UpdatedLocalDataSource,
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationDataSource,
    private val dateUtils: DateUtils,
    private val networkStatus: NetworkStatus
) : ViewModel() {

    var isLoading = mutableStateOf(false)

    val observableItemNumber = MutableStateFlow(0)

    private var _citiesWeather:MutableStateFlow<CommonScreenState> = MutableStateFlow(CommonScreenState.Loading)

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

        viewModelScope.launch {
            citiesWeather.collect {
                it
            }
        }

        initCitiesWeatherFlow()
//        getWeatherData()


    }

    fun initCitiesWeatherFlow() {
        viewModelScope.launch {
            updatedLocalDataSource.getWeatherFlow().collectLatest {
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
            citiesWeather.debounce(500L).collectLatest { state ->
                when (state) {
                    is CommonScreenState.Empty -> {
                        if (isLocationAvailable) {
                            viewModelScope.launch {
                                locationService.getLocationUpdate()
                                    .flatMapLatest { coordinatesWrapper ->
                                        weatherRepository.fetchCurrentLocationWeather(
                                            coordinatesWrapper,
                                            true
                                        )
                                    }.launchIn(this)
                            }
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
            viewModelScope.launch {
                val number = observableItemNumber.value
                val item =
                    (citiesWeather.value as? CommonScreenState.Success)?.updatedWeatherList?.get(
                        number
                    )

                item?.let { updatedWeatherItem ->
                    weatherRepository.getCityWeatherFlow(updatedWeatherItem.cityItem)
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
}