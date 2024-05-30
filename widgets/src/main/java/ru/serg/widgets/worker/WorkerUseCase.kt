@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.widgets.worker

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import ru.serg.local.LocalDataSource
import ru.serg.location.LocationService
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationService
) {

    private fun fetchFavouriteCity() =
        localDataSource.getFavouriteCity().flatMapLatest {
            weatherRepository.getCityWeatherFlow(it)
        }

    fun fetchData() = if (locationService.isLocationAvailable()) {
        fetchCoordinatesData()
    } else {
        fetchFavouriteCity()
    }


    private fun fetchCoordinatesData() =
        locationService.getLocationUpdate().flatMapLatest {
            weatherRepository.fetchCurrentLocationWeather(it)
        }


    fun updateLocalData() = localDataSource.getFavouriteCityWeather()
}
