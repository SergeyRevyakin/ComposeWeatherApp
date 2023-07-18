package ru.serg.composeweatherapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdatedDailyTempItem(
    val morningTemp: Double,
    val dayTemp: Double,
    val eveningTemp: Double,
    val nightTemp: Double,
    val maxTemp: Double?,
    val minTemp: Double?
)