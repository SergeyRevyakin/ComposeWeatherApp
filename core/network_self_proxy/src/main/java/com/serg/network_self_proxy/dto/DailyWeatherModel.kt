package com.serg.network_self_proxy.dto

import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherModel(
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val weatherDescription: String?,
    val weatherIcon: Int?,
    val dateTime: Long?,
    val sunrise: Long?,
    val sunset: Long?,
    val uvi: Double?,
    val precipitationProbability: Int?,
    val maxTemp: Double?,
    val minTemp: Double?,
    val feelsLikeMaxTemp: Double?,
    val feelsLikeMinTemp: Double?,
)