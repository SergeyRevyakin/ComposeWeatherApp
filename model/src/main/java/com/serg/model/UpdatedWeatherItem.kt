package com.serg.model

data class UpdatedWeatherItem(
    val cityItem: CityItem,
    val dailyWeatherList: List<DailyWeather>,
    val hourlyWeatherList: List<HourlyWeather>
)

data class DailyWeather(
    val windDirection: Int,
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Int,
    val weatherDescription: String,
    val weatherIcon: Int,
    val dateTime: Long,
    val sunrise: Long,
    val sunset: Long,
    val dailyWeatherItem: UpdatedDailyTempItem,
    val feelsLike: UpdatedDailyTempItem,
    val uvi: Double
)

data class HourlyWeather(
    val feelsLike: Double,
    val currentTemp: Double,
    val windDirection: Int,
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Int,
    val weatherDescription: String,
    val weatherIcon: Int,
    val dateTime: Long,
    val uvi: Double
)
