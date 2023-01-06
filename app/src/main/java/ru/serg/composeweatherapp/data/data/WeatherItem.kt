package ru.serg.composeweatherapp.data.data

import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.R

data class WeatherItem(
    val feelsLike: Double?,
    val currentTemp: Double?,
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val weatherDescription: String?,
    val weatherIcon: Int?,
    val dateTime: Long?,
    val cityItem: CityItem?,
    val lastUpdatedTime: Long,
    val hourlyWeatherList: List<HourWeatherItem>,
    val dailyWeatherList: List<DayWeatherItem>
) {
    companion object {
        fun defaultItem() = WeatherItem(
            feelsLike = 12.5,
            currentTemp = 15.0,
            windDirection = 90,
            windSpeed = 5.2,
            humidity = 52,
            pressure = 980,
            weatherDescription = "Sunny",
            weatherIcon = R.drawable.ic_day_sunny,
            dateTime = getTimeMillis(),
            cityItem = CityItem(
                name = "Moscow"
            ),
            lastUpdatedTime = getTimeMillis(),
            hourlyWeatherList = listOf(),
            dailyWeatherList = listOf()
        )
    }
}