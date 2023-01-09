package ru.serg.composeweatherapp.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityNameGeocodingResponseItem(
    val country: String?,
    val lat: Double?,
    @SerialName("local_names")
    val localNames: Map<String, String>? = mapOf(),
    val lon: Double?,
    val name: String?,
    val state: String?,
    val message: String?,
)