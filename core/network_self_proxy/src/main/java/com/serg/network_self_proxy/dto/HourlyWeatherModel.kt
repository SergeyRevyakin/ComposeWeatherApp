package com.serg.network_self_proxy.dto

import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherModel(
    val feelsLike: Double?,
    val currentTemp: Double?,
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val weatherDescription: String?,
    val weatherIcon: Int?,
    val dateTime: Long?,
    val uvi: Double?,
    val precipitationProbability: Int?
)
