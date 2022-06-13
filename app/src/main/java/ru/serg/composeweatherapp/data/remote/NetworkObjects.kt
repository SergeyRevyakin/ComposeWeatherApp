package ru.serg.composeweatherapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class WeatherResult {
    @Serializable
    data class Success(
        val lat: Double,
        val lon: Double,
        val timezone: String,
        @SerialName("timezone_offset")
        val timezoneOffset: Long,
        val current: NetworkCurrent,
        val hourly: List<NetworkHourly>,
        val daily: List<NetworkDaily>,
        val alerts: List<NetworkAlert>?,
    ) : WeatherResult()

    data class Failure(
        val error: String
    ) : WeatherResult()

    object Loading : WeatherResult()
}

@Serializable
data class Precipitation(
    @SerialName("1h") val hour: Double
)

@Serializable
data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

@Serializable
data class NetworkCurrent(
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    @SerialName("dew_point") val dewPoint: Double,
    val clouds: Double,
    val uvi: Double,
    val visibility: Double,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_gust") val windGust: Double?,
    @SerialName("wind_deg") val windDeg: Int,
    val rain: Precipitation? = null,
    val snow: Precipitation? = null,
    val weather: List<Weather>
)

@Serializable
data class NetworkHourly(
    val dt: Long,
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    @SerialName("dew_point") val dewPoint: Double,
    val clouds: Double,
    val uvi: Double,
    val visibility: Double,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_gust") val windGust: Double?,
    @SerialName("wind_deg") val windDeg: Int,
    val pop: Double,
    val rain: Precipitation? = null,
    val snow: Precipitation? = null,
    val weather: List<Weather>
)

@Serializable
data class NetworkDaily(
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val moonrise: Long? = null,
    val moonset: Long? = null,
    @SerialName("moon_phase") val moonPhase: Double,
    val temp: Temp,
    @SerialName("feels_like") val feelsLike: FeelsLike? = null,
    val pressure: Double,
    val humidity: Double,
    @SerialName("dew_point") val dewPoint: Double,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_gust") val windGust: Double?,
    @SerialName("wind_deg") val windDeg: Int,
    val uvi: Double,
    val pop: Double,
    val rain: Double? = null,
    val snow: Double? = null,
    val weather: List<Weather>
) {
    @Serializable
    data class Temp(
        val morn: Double,
        val day: Double,
        val eve: Double,
        val night: Double,
        val min: Double,
        val max: Double,
    )

    @Serializable
    data class FeelsLike(
        val morn: Double,
        val day: Double,
        val eve: Double,
        val night: Double,
    )
}

@Serializable
data class NetworkAlert(
    @SerialName("sender_name") val senderName: String,
    val event: String,
    val start: Long,
    val end: Long,
    val description: String
)
