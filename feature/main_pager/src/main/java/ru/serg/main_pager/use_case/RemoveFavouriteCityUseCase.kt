package ru.serg.main_pager.use_case

import ru.serg.model.WeatherItem
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class RemoveFavouriteCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(weatherItem: WeatherItem) =
        weatherRepository.removeFavouriteCityParam(weatherItem)
}