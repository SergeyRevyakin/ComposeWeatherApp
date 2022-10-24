package ru.serg.composeweatherapp.data.data

import kotlinx.serialization.Serializable

@Serializable
data class HourWeatherItem(
    val weatherIcon: Int,
    val currentTemp: Double,
    val timestamp: Long
)