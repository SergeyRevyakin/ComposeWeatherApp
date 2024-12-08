package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQuality(
    @SerialName("co")
    val co: Double? = null,
    @SerialName("gb-defra-index")
    val gbDefraIndex: Int? = null,
    @SerialName("no2")
    val no2: Double? = null,
    @SerialName("o3")
    val o3: Double? = null,
    @SerialName("pm10")
    val pm10: Double? = null,
    @SerialName("pm2_5")
    val pm25: Double? = null,
    @SerialName("so2")
    val so2: Double? = null,
    @SerialName("us-epa-index")
    val usEpaIndex: Int? = null
)