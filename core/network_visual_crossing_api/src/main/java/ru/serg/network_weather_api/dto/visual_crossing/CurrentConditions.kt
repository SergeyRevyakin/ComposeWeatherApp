package ru.serg.network_weather_api.dto.visual_crossing


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentConditions(
    @SerialName("cloudcover")
    val cloudcover: Double?,
    @SerialName("conditions")
    val conditions: String?,
    @SerialName("datetime")
    val datetime: String?,
    @SerialName("datetimeEpoch")
    val datetimeEpoch: Int?,
    @SerialName("dew")
    val dew: Double?,
    @SerialName("feelslike")
    val feelslike: Double?,
    @SerialName("humidity")
    val humidity: Double?,
    @SerialName("icon")
    val icon: String?,
    @SerialName("moonphase")
    val moonphase: Double?,
    @SerialName("precip")
    val precip: Double?,
    @SerialName("precipprob")
    val precipprob: Double?,
    @SerialName("preciptype")
    val preciptype: List<String?>?,
    @SerialName("pressure")
    val pressure: Double?,
    @SerialName("snow")
    val snow: Double?,
    @SerialName("snowdepth")
    val snowdepth: Double?,
    @SerialName("solarenergy")
    val solarenergy: Double?,
    @SerialName("solarradiation")
    val solarradiation: Double?,
    @SerialName("source")
    val source: String?,
    @SerialName("stations")
    val stations: List<String?>?,
    @SerialName("sunrise")
    val sunrise: String?,
    @SerialName("sunriseEpoch")
    val sunriseEpoch: Int?,
    @SerialName("sunset")
    val sunset: String?,
    @SerialName("sunsetEpoch")
    val sunsetEpoch: Int?,
    @SerialName("temp")
    val temp: Double?,
    @SerialName("uvindex")
    val uvindex: Double?,
    @SerialName("visibility")
    val visibility: Double?,
    @SerialName("winddir")
    val winddir: Double?,
    @SerialName("windgust")
    val windgust: Double?,
    @SerialName("windspeed")
    val windspeed: Double?
)