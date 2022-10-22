package ru.serg.composeweatherapp.data.data

data class WeatherItem(
    val feelsLike: Double?,
    val currentTemp: Double,
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
) {
}