@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.main_pager.updated_pager

import androidx.lifecycle.ViewModel
import com.serg.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.location.LocationDataSource
import ru.serg.main_pager.DateUseCase
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationDataSource,
    private val dateUseCase: DateUseCase
) : ViewModel() {

//    private var weatherFlow = MutableSharedFlow<UpdatedWeatherItem>(replay = 1)
//    private lateinit var weatherStateFlow: MutableStateFlow<WeatherState>// = MutableStateFlow(WeatherState.Init(weatherItem))
//
//    fun initialize(weatherItem: UpdatedWeatherItem) {
//        viewModelScope.launch {
//            if (!::weatherStateFlow.isInitialized) weatherStateFlow =
//                MutableStateFlow(WeatherState.Init(weatherItem))
//
//
//            weatherFlow.collectLatest {
//                when {
//                    it.dailyWeatherList.isEmpty() || it.hourlyWeatherList.isEmpty() -> {
//                        refresh(it.cityItem)
//                        weatherStateFlow.tryEmit(
//                            WeatherState.NoWeather(it)
//                        )
//                    }
//
//                    dateUseCase.isFetchDateExpired(it.cityItem.lastTimeUpdated) -> {
//                        refresh(it.cityItem)
//                        weatherStateFlow.tryEmit(
//                            WeatherState.Loading(it)
//                        )
//                    }
//
//                    else -> weatherStateFlow.tryEmit(WeatherState.Success(it))
//                }
//            }
//            weatherFlow.tryEmit(weatherItem)
//        }
//    }
//
//    fun refresh(city: CityItem) {
////        _isRefreshing.compareAndSet(expect = false, update = true)
//        viewModelScope.launch {
//            if (city.isFavorite) {
//                locationService.getLocationUpdate().flatMapLatest { coordinatesWrapper ->
//                    weatherRepository.fetchCurrentLocationWeather(
//                        coordinatesWrapper,
//                    )
//                }.launchIn(this)
//            } else weatherRepository.getCityWeatherFlow(city)
//        }
//    }
//
//    fun refresh() {
//        viewModelScope.launch {
//            weatherFlow.collectLatest {
//                val city = it.cityItem
//                if (city.isFavorite) {
//                    locationService.getLocationUpdate().flatMapLatest { coordinatesWrapper ->
//                        weatherRepository.fetchCurrentLocationWeather(
//                            coordinatesWrapper,
//                        )
//                    }.launchIn(this)
//                } else weatherRepository.getCityWeatherFlow(city)
//            }
//        }
//    }

}