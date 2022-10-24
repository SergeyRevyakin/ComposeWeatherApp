package ru.serg.composeweatherapp.data.data

import kotlinx.serialization.Serializable

@Serializable
data class IntraDayTempItem(
    val morningTemp: Double?,
    val dayTemp: Double?,
    val eveningTemp: Double?,
    val nightTemp: Double?,
)