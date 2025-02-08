package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alert(
    @SerialName("areas")
    val areas: String? = null,
    @SerialName("category")
    val category: String? = null,
    @SerialName("certainty")
    val certainty: String? = null,
    @SerialName("desc")
    val desc: String? = null,
    @SerialName("effective")
    val effective: String? = null,
    @SerialName("event")
    val event: String? = null,
    @SerialName("expires")
    val expires: String? = null,
    @SerialName("headline")
    val headline: String? = null,
    @SerialName("instruction")
    val instruction: String? = null,
    @SerialName("msgtype")
    val msgtype: String? = null,
    @SerialName("note")
    val note: String? = null,
    @SerialName("severity")
    val severity: String? = null,
    @SerialName("urgency")
    val urgency: String? = null
)