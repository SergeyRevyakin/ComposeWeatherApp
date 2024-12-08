package ru.serg.network_weather_api.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.serg.network_weather_api.dto.visual_crossing.Alert
import ru.serg.network_weather_api.dto.visual_crossing.CurrentConditions
import ru.serg.network_weather_api.dto.visual_crossing.Day
import ru.serg.network_weather_api.dto.visual_crossing.StationDescription

@Serializable
data class VisualCrossingResponse(
    @SerialName("address")
    val address: String?,
    @SerialName("alerts")
    val alerts: List<Alert>?,
    @SerialName("currentConditions")
    val currentConditions: CurrentConditions?,
    @SerialName("days")
    val days: List<Day?>?,
    @SerialName("description")
    val description: String?,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("queryCost")
    val queryCost: Int?,
    @SerialName("resolvedAddress")
    val resolvedAddress: String?,
    @SerialName("stations")
    val stations: Map<String, StationDescription>?,
    @SerialName("timezone")
    val timezone: String?,
    @SerialName("tzoffset")
    val tzoffset: Double?
)