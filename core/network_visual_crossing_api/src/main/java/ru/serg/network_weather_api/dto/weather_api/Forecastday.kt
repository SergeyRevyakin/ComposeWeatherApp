package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecastday(
    @SerialName("astro")
    val astro: Astro? = Astro(),
    @SerialName("date")
    val date: String? = "",
    @SerialName("date_epoch")
    val dateEpoch: Int? = 0,
    @SerialName("day")
    val day: Day? = Day(),
    @SerialName("hour")
    val hour: List<Hour>? = listOf()
)