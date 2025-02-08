package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hour(
    @SerialName("chance_of_rain")
    val chanceOfRain: Int? = 0,
    @SerialName("chance_of_snow")
    val chanceOfSnow: Int? = 0,
    @SerialName("cloud")
    val cloud: Int? = 0,
    @SerialName("condition")
    val condition: Condition? = Condition(),
    @SerialName("dewpoint_c")
    val dewpointC: Double? = 0.0,
    @SerialName("dewpoint_f")
    val dewpointF: Double? = 0.0,
    @SerialName("feelslike_c")
    val feelslikeC: Double? = 0.0,
    @SerialName("feelslike_f")
    val feelslikeF: Double? = 0.0,
    @SerialName("gust_kph")
    val gustKph: Double? = 0.0,
    @SerialName("gust_mph")
    val gustMph: Double? = 0.0,
    @SerialName("heatindex_c")
    val heatindexC: Double? = 0.0,
    @SerialName("heatindex_f")
    val heatindexF: Double? = 0.0,
    @SerialName("humidity")
    val humidity: Int? = 0,
    @SerialName("is_day")
    val isDay: Int? = 0,
    @SerialName("precip_in")
    val precipIn: Double? = 0.0,
    @SerialName("precip_mm")
    val precipMm: Double? = 0.0,
    @SerialName("pressure_in")
    val pressureIn: Double? = 0.0,
    @SerialName("pressure_mb")
    val pressureMb: Double? = 0.0,
    @SerialName("snow_cm")
    val snowCm: Double? = 0.0,
    @SerialName("temp_c")
    val tempC: Double? = 0.0,
    @SerialName("temp_f")
    val tempF: Double? = 0.0,
    @SerialName("time")
    val time: String? = "",
    @SerialName("time_epoch")
    val timeEpoch: Int? = 0,
    @SerialName("uv")
    val uv: Double? = 0.0,
    @SerialName("vis_km")
    val visKm: Double? = 0.0,
    @SerialName("vis_miles")
    val visMiles: Double? = 0.0,
    @SerialName("will_it_rain")
    val willItRain: Int? = 0,
    @SerialName("will_it_snow")
    val willItSnow: Int? = 0,
    @SerialName("wind_degree")
    val windDegree: Int? = 0,
    @SerialName("wind_dir")
    val windDir: String? = "",
    @SerialName("wind_kph")
    val windKph: Double? = 0.0,
    @SerialName("wind_mph")
    val windMph: Double? = 0.0,
    @SerialName("windchill_c")
    val windchillC: Double? = 0.0,
    @SerialName("windchill_f")
    val windchillF: Double? = 0.0,
    @SerialName("air_quality")
    val airQuality: AirQuality?
)