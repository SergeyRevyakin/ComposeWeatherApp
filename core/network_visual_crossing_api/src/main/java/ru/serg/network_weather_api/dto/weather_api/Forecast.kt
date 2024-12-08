package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    @SerialName("forecastday")
    val forecastday: List<Forecastday>? = listOf()
)