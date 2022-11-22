package ru.serg.composeweatherapp.data.data

import kotlinx.serialization.Serializable
import ru.serg.composeweatherapp.utils.Constants

@Serializable
data class CityItem(
    val name: String,
    val country: String = Constants.EMPTY_STRING,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isFavorite: Boolean = false
)