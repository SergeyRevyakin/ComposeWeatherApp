package ru.serg.model

data class HourlyWeather(
    val feelsLike: Double,
    val currentTemp: Double,
    val windDirection: Int,
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Int,
    val weatherDescription: String,
    val weatherIcon: Int,
    val dateTime: Long,
    val uvi: Double
)
