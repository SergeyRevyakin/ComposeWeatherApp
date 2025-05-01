package ru.serg.main_pager.use_case

import ru.serg.model.CityItem
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(cityItem: CityItem) = weatherRepository.fetchCityWeatherFlow(cityItem)
}