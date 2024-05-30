package ru.serg.model

data class DailyWeather(
    val windDirection: Int,
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Int,
    val weatherDescription: String,
    val weatherIcon: Int,
    val dateTime: Long,
    val sunrise: Long,
    val sunset: Long,
    val dailyWeatherItem: DailyTempItem,
    val feelsLike: DailyTempItem,
    val uvi: Double
)