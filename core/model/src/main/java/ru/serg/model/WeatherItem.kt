package ru.serg.model

data class WeatherItem(
    val cityItem: CityItem,
    val dailyWeatherList: List<DailyWeather>,
    val hourlyWeatherList: List<HourlyWeather>,
    val alertList: List<AlertItem>,
    val alertMessage: String? = null
)