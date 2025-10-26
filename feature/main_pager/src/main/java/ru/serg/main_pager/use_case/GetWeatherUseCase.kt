package ru.serg.main_pager.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.serg.local.LocalDataSource
import ru.serg.location.LocationService
import ru.serg.model.WeatherItem
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val locationService: LocationService,
    private val weatherRepository: WeatherRepository,
) {

    operator fun invoke(page: Int, isLocationAvailable: Boolean = false): Flow<List<WeatherItem>> {
        return localDataSource.getWeatherFlow()
            .distinctUntilChanged()
            .map {
                if (it.isEmpty()) {
                    if (isLocationAvailable) locationService.getLocationUpdate(
                        isOneTimeRequest = true,
                    ).collectLatest { coordinates ->
                        weatherRepository.fetchLocationWeather(coordinates)
                    }
                }
                val currentItem = it.getOrNull(page) ?: return@map it
                if (isExpired(
                        currentItem.cityItem.lastTimeUpdated,
                        0.25
                    ) || currentItem.dailyWeatherList.isEmpty() || currentItem.hourlyWeatherList.isEmpty()
                ) {
                    if (currentItem.cityItem.isFavorite && isLocationAvailable) {
                        locationService.getLocationUpdate(
                            isOneTimeRequest = true,
                        ).collectLatest { coordinates ->
                            weatherRepository.fetchLocationWeather(coordinates)
                        }
                    } else {
                        weatherRepository.fetchCityWeather(currentItem.cityItem)
                    }
                }
                it
            }
    }


    private fun isExpired(
        timestamp: Long,
        frequency: Double
    ): Boolean {
        return (frequency * 60L * 60L * 1000L + timestamp) - System.currentTimeMillis() < 0
    }
}