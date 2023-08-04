@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.ui.screens.updated_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.WeatherRepository
import ru.serg.composeweatherapp.data.data_source.LocationDataSource
import ru.serg.composeweatherapp.ui.screens.WeatherState
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.model.CityItem
import ru.serg.model.UpdatedWeatherItem
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationDataSource,
    private val dateUtils: DateUtils
) : ViewModel() {

    private var weatherFlow = MutableSharedFlow<UpdatedWeatherItem>(replay = 1)
    private lateinit var weatherStateFlow: MutableStateFlow<WeatherState>// = MutableStateFlow(WeatherState.Init(weatherItem))

    fun initialize(weatherItem: UpdatedWeatherItem) {
        viewModelScope.launch {
            if (!::weatherStateFlow.isInitialized) weatherStateFlow =
                MutableStateFlow(WeatherState.Init(weatherItem))


            weatherFlow.collectLatest {
                when {
                    it.dailyWeatherList.isEmpty() || it.hourlyWeatherList.isEmpty() -> {
                        refresh(it.cityItem)
                        weatherStateFlow.tryEmit(
                            WeatherState.NoWeather(it)
                        )
                    }

                    dateUtils.isFetchDateExpired(it.cityItem.lastTimeUpdated) -> {
                        refresh(it.cityItem)
                        weatherStateFlow.tryEmit(
                            WeatherState.Loading(it)
                        )
                    }

                    else -> weatherStateFlow.tryEmit(WeatherState.Success(it))
                }
            }
            weatherFlow.tryEmit(weatherItem)
        }
    }

    fun refresh(city: CityItem) {
//        _isRefreshing.compareAndSet(expect = false, update = true)
        viewModelScope.launch {
            if (city.isFavorite) {
                locationService.getLocationUpdate().flatMapLatest { coordinatesWrapper ->
                    weatherRepository.fetchCurrentLocationWeather(
                        coordinatesWrapper,
                        true
                    )
                }.launchIn(this)
            } else weatherRepository.fetchCityWeather(city, true).launchIn(this)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            weatherFlow.collectLatest {
                val city = it.cityItem
                if (city.isFavorite) {
                    locationService.getLocationUpdate().flatMapLatest { coordinatesWrapper ->
                        weatherRepository.fetchCurrentLocationWeather(
                            coordinatesWrapper,
                            true
                        )
                    }.launchIn(this)
                } else weatherRepository.fetchCityWeather(city, true).launchIn(this)
            }
        }
    }

}