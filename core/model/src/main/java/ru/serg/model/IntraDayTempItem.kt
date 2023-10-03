package ru.serg.model

import kotlinx.serialization.Serializable

@Serializable
data class IntraDayTempItem(
    val morningTemp: Double,
    val dayTemp: Double,
    val eveningTemp: Double,
    val nightTemp: Double,
)