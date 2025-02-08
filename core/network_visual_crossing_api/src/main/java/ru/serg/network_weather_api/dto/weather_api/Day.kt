package ru.serg.network_weather_api.dto.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Day(
    @SerialName("avghumidity")
    val avghumidity: Int? = 0,
    @SerialName("avgtemp_c")
    val avgtempC: Double? = 0.0,
    @SerialName("avgtemp_f")
    val avgtempF: Double? = 0.0,
    @SerialName("avgvis_km")
    val avgvisKm: Double? = 0.0,
    @SerialName("avgvis_miles")
    val avgvisMiles: Double? = 0.0,
    @SerialName("condition")
    val condition: Condition? = Condition(),
    @SerialName("daily_chance_of_rain")
    val dailyChanceOfRain: Int? = 0,
    @SerialName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int? = 0,
    @SerialName("daily_will_it_rain")
    val dailyWillItRain: Int? = 0,
    @SerialName("daily_will_it_snow")
    val dailyWillItSnow: Int? = 0,
    @SerialName("maxtemp_c")
    val maxtempC: Double? = 0.0,
    @SerialName("maxtemp_f")
    val maxtempF: Double? = 0.0,
    @SerialName("maxwind_kph")
    val maxwindKph: Double? = 0.0,
    @SerialName("maxwind_mph")
    val maxwindMph: Double? = 0.0,
    @SerialName("mintemp_c")
    val mintempC: Double? = 0.0,
    @SerialName("mintemp_f")
    val mintempF: Double? = 0.0,
    @SerialName("totalprecip_in")
    val totalprecipIn: Double? = 0.0,
    @SerialName("totalprecip_mm")
    val totalprecipMm: Double? = 0.0,
    @SerialName("totalsnow_cm")
    val totalsnowCm: Double? = 0.0,
    @SerialName("uv")
    val uv: Double? = 0.0
)