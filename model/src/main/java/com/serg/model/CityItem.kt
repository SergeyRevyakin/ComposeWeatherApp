package com.serg.model

import kotlinx.serialization.Serializable

@Serializable
data class CityItem(
    val name: String,
    val country: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isFavorite: Boolean = false,
    val id: Int = 0,
    val lastTimeUpdated: Long = 0L
)