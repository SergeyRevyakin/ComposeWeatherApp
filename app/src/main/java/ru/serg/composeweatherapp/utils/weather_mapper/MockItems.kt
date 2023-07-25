package ru.serg.composeweatherapp.utils.weather_mapper

import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.dto.DailyWeather
import ru.serg.composeweatherapp.data.dto.HourlyWeather
import ru.serg.composeweatherapp.data.dto.UpdatedDailyTempItem

object MockItems {
    fun getDailyWeatherMockItem() =
    DailyWeather(
        windDirection = 90,
        windSpeed = 2.4,
        humidity = 60,
        pressure = 980,
        weatherDescription = "Windy",
        weatherIcon = R.drawable.ic_windy,
        sunrise = getTimeMillis() - 100000L,
        sunset = getTimeMillis() + 100000L,
        dailyWeatherItem = getUpdatedDailyTempMockItem(),
        feelsLike = getUpdatedDailyTempMockItem(),
        dateTime = getTimeMillis(),
        uvi = 3.2
    )
    fun getHourlyWeatherMockItem() =
    HourlyWeather(
        windDirection = 90,
        windSpeed = 2.4,
        humidity = 60,
        pressure = 980,
        weatherDescription = "Windy",
        weatherIcon = R.drawable.ic_windy,
        currentTemp = 20.1,
        feelsLike = 23.3,
        dateTime = getTimeMillis(),
        uvi = 2.2
    )

    private fun getUpdatedDailyTempMockItem() =
        UpdatedDailyTempItem(
            morningTemp = 14.5,
            dayTemp = 21.2,
            eveningTemp = 18.1,
            nightTemp = 12.9,
            maxTemp = 22.2,
            minTemp = 12.1
        )
}