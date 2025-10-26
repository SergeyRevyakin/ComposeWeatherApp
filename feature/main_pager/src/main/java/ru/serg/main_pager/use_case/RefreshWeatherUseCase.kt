package ru.serg.main_pager.use_case

import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class RefreshWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {

    suspend operator fun invoke(cityItem: CityItem) =
        if (cityItem.isFavorite) {
            weatherRepository.fetchLocationWeather(
                Coordinates(
                    latitude = cityItem.latitude,
                    longitude = cityItem.longitude,
                )
            )
        } else {
            weatherRepository.fetchCityWeather(cityItem)
        }
}