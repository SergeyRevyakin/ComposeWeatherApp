package ru.serg.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyTempItem(
    val morningTemp: Double,
    val dayTemp: Double,
    val eveningTemp: Double,
    val nightTemp: Double,
    val maxTemp: Double?,
    val minTemp: Double?
)