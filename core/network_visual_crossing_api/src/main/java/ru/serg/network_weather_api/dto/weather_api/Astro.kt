package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Astro(
    @SerialName("is_moon_up")
    val isMoonUp: Int? = null,
    @SerialName("is_sun_up")
    val isSunUp: Int? = null,
    @SerialName("moon_illumination")
    val moonIllumination: Int? = null,
    @SerialName("moon_phase")
    val moonPhase: String? = null,
    @SerialName("moonrise")
    val moonrise: String? = null,
    @SerialName("moonset")
    val moonset: String? = null,
    @SerialName("sunrise")
    val sunrise: String? = null,
    @SerialName("sunset")
    val sunset: String? = null
)