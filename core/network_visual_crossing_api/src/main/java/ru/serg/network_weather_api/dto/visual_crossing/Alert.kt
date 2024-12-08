package ru.serg.network_weather_api.dto.visual_crossing


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alert(
    @SerialName("description")
    val description: String?,
    @SerialName("ends")
    val ends: String?,
    @SerialName("endsEpoch")
    val endsEpoch: Int?,
    @SerialName("event")
    val event: String?,
    @SerialName("headline")
    val headline: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("language")
    val language: String?,
    @SerialName("link")
    val link: String?,
    @SerialName("onset")
    val onset: String?,
    @SerialName("onsetEpoch")
    val onsetEpoch: Int?
)