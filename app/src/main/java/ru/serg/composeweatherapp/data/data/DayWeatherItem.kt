package ru.serg.composeweatherapp.data.data

import kotlinx.serialization.Serializable

@Serializable
data class DayWeatherItem(
    val feelsLike: IntraDayTempItem,
    val temp: IntraDayTempItem,
    val windDirection: Int,
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Int,
    val weatherDescription: String,
    val weatherIcon: Int,
    val dateTime: Long,
    val sunrise: Long,
    val sunset: Long
)