package ru.serg.main_pager.use_case

import ru.serg.model.Coordinates
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class GetLocationWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(coordinates: Coordinates) =
        weatherRepository.fetchLocationWeather(coordinates)
}