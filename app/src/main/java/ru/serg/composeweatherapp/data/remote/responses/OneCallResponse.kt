package ru.serg.composeweatherapp.data.remote.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneCallResponse(
    val current: Current?,
    val daily: List<Daily?>?,
    val hourly: List<Hourly?>?,
    val lat: Double?,
    val lon: Double?,
    val timezone: String?,
    @SerialName("timezone_offset")
    val timezoneOffset: Int?,
    val message: String?,
) {
    @Serializable
    data class Current(
        val clouds: Int?,
        @SerialName("dew_point")
        val dewPoint: Double?,
        val dt: Long?,
        @SerialName("feels_like")
        val feelsLike: Double?,
        val humidity: Int?,
        val pressure: Int?,
        val sunrise: Int?,
        val sunset: Int?,
        val temp: Double?,
        val uvi: Double?,
        val visibility: Int?,
        val weather: List<Weather?>?,
        @SerialName("wind_deg")
        val windDeg: Int?,
        @SerialName("wind_gust")
        val windGust: Double?,
        @SerialName("wind_speed")
        val windSpeed: Double?
    ) {
        @Serializable
        data class Weather(
            val description: String?,
            val icon: String?,
            val id: Int?,
            val main: String?
        )
    }

    @Serializable
    data class Daily(
        val clouds: Int?,
        @SerialName("dew_point")
        val dewPoint: Double?,
        val dt: Long?,
        @SerialName("feels_like")
        val feelsLike: FeelsLike?,
        val humidity: Int?,
        @SerialName("moon_phase")
        val moonPhase: Double?,
        val moonrise: Int?,
        val moonset: Int?,
        val pop: Double?,
        val pressure: Int?,
        val rain: Double?,
        val sunrise: Int?,
        val sunset: Int?,
        val temp: Temp?,
        val uvi: Double?,
        val weather: List<Weather?>?,
        @SerialName("wind_deg")
        val windDeg: Int?,
        @SerialName("wind_gust")
        val windGust: Double?,
        @SerialName("wind_speed")
        val windSpeed: Double?
    ) {
        @Serializable
        data class FeelsLike(
            val day: Double?,
            val eve: Double?,
            val morn: Double?,
            val night: Double?
        )

        @Serializable
        data class Temp(
            val day: Double?,
            val eve: Double?,
            val max: Double?,
            val min: Double?,
            val morn: Double?,
            val night: Double?
        )

        @Serializable
        data class Weather(
            val description: String?,
            val icon: String?,
            val id: Int?,
            val main: String?
        )
    }

    @Serializable
    data class Hourly(
        val clouds: Int?,
        @SerialName("dew_point")
        val dewPoint: Double?,
        val dt: Long?,
        @SerialName("feels_like")
        val feelsLike: Double?,
        val humidity: Int?,
        val pop: Double?,
        val pressure: Int?,
        val rain: Rain?,
        val temp: Double?,
        val uvi: Double?,
        val visibility: Int?,
        val weather: List<Weather?>?,
        @SerialName("wind_deg")
        val windDeg: Int?,
        @SerialName("wind_gust")
        val windGust: Double?,
        @SerialName("wind_speed")
        val windSpeed: Double?
    ) {
        companion object {
            fun getTestExample(): Hourly {
                return Hourly(
                    dt = 1656421200,
                    temp = 23.52,
                    feelsLike = 20.1,
                    pressure = 1230,
                    humidity = 55,
                    dewPoint = 8.6,
                    uvi = 5.5,
                    clouds = 12,
                    visibility = 10000,
                    windSpeed = 6.6,
                    windDeg = 225,
                    windGust = 12.5,
                    pop = 0.0,
                    weather = listOf(
                        Weather(
                            id = 801,
                            main = "Clouds",
                            description = "few clouds",
                            icon = "123"
                        )
                    ),
                    rain = null
                )
            }
        }

        @Serializable
        data class Rain(
            @SerialName("1h")
            val h: Double?
        )

        @Serializable
        data class Weather(
            val description: String?,
            val icon: String?,
            val id: Int?,
            val main: String?
        )
    }
}