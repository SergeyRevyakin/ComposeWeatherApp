package ru.serg.composeweatherapp.data.dto

data class UpdatedDailyWeatherItem(
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val weatherDescription: String?,
    val weatherIcon: Int?,
    val dateTime: Long?,
    val cityItem: CityItem?,
    val alertMessage: String? = null,
    val sunrise: Long?,
    val sunset: Long?,
    val dailyTempItem: UpdatedDailyTempItem,
    val feelsLikeTemp: UpdatedDailyTempItem
)
