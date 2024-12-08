package com.serg.network_self_proxy.dto

import com.serg.weather_proxy.utils.model.CityModel
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val dailyList: List<DailyWeatherModel>?,
    val hourlyList: List<HourlyWeatherModel>?,
    val airPollutionList: List<AirPollutionModel>?,
    val alertList: List<AlertModel>?,
    val city: CityModel?,
)