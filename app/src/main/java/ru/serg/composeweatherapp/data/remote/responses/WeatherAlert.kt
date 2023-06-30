package ru.serg.composeweatherapp.data.remote.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherAlert(
    @SerialName("description")
    val description: String?,
    @SerialName("end")
    val end: Int?,
    @SerialName("event")
    val event: String?,
    @SerialName("sender_name")
    val senderName: String?,
    @SerialName("start")
    val start: Int?
)