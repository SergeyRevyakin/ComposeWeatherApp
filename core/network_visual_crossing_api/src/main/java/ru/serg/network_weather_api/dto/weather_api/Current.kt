package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("air_quality")
    val airQuality: AirQuality? = null,
    @SerialName("cloud")
    val cloud: Int? = null,
    @SerialName("condition")
    val condition: Condition? = null,
    @SerialName("dewpoint_c")
    val dewpointC: Double? = null,
    @SerialName("dewpoint_f")
    val dewpointF: Double? = null,
    @SerialName("feelslike_c")
    val feelslikeC: Double? = null,
    @SerialName("feelslike_f")
    val feelslikeF: Double? = null,
    @SerialName("gust_kph")
    val gustKph: Double? = null,
    @SerialName("gust_mph")
    val gustMph: Double? = null,
    @SerialName("heatindex_c")
    val heatindexC: Double? = null,
    @SerialName("heatindex_f")
    val heatindexF: Double? = null,
    @SerialName("humidity")
    val humidity: Int? = null,
    @SerialName("is_day")
    val isDay: Int? = null,
    @SerialName("last_updated")
    val lastUpdated: String? = null,
    @SerialName("last_updated_epoch")
    val lastUpdatedEpoch: Int? = null,
    @SerialName("precip_in")
    val precipIn: Double? = null,
    @SerialName("precip_mm")
    val precipMm: Double? = null,
    @SerialName("pressure_in")
    val pressureIn: Double? = null,
    @SerialName("pressure_mb")
    val pressureMb: Double? = null,
    @SerialName("temp_c")
    val tempC: Double? = null,
    @SerialName("temp_f")
    val tempF: Double? = null,
    @SerialName("uv")
    val uv: Double? = null,
    @SerialName("vis_km")
    val visKm: Double? = null,
    @SerialName("vis_miles")
    val visMiles: Double? = null,
    @SerialName("wind_degree")
    val windDegree: Int? = null,
    @SerialName("wind_dir")
    val windDir: String? = null,
    @SerialName("wind_kph")
    val windKph: Double? = null,
    @SerialName("wind_mph")
    val windMph: Double? = null,
    @SerialName("windchill_c")
    val windchillC: Double? = null,
    @SerialName("windchill_f")
    val windchillF: Double? = null
)