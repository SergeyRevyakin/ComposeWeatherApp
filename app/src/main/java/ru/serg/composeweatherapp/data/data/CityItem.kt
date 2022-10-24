package ru.serg.composeweatherapp.data.data

data class CityItem(
    val name: String,
    val country: String?,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isFavorite: Boolean = false
)