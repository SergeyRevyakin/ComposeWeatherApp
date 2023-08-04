package ru.serg.model

import kotlinx.serialization.Serializable

@Serializable
data class HourWeatherItem(
    val weatherIcon: Int,
    val currentTemp: Double,
    val timestamp: Long
)