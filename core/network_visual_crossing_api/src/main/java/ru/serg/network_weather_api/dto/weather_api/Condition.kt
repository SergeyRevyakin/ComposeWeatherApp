package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Condition(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("icon")
    val icon: String? = null,
    @SerialName("text")
    val text: String? = null
)