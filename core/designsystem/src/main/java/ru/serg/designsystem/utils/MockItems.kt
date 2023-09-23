package ru.serg.designsystem.utils

import ru.serg.drawables.R.drawable
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.UpdatedDailyTempItem

object MockItems {
    fun getDailyWeatherMockItem() =
        DailyWeather(
            windDirection = 90,
            windSpeed = 2.4,
            humidity = 60,
            pressure = 980,
            weatherDescription = "Windy",
            weatherIcon = drawable.ic_windy,
            sunrise = System.currentTimeMillis() - 100000L,
            sunset = System.currentTimeMillis() + 100000L,
            dailyWeatherItem = getUpdatedDailyTempMockItem(),
            feelsLike = getUpdatedDailyTempMockItem(),
            dateTime = System.currentTimeMillis(),
            uvi = 3.2
        )

    fun getHourlyWeatherMockItem() =
        HourlyWeather(
            windDirection = 90,
            windSpeed = 2.4,
            humidity = 60,
            pressure = 980,
            weatherDescription = "Windy",
            weatherIcon = drawable.ic_windy,
            currentTemp = 20.1,
            feelsLike = 23.3,
            dateTime = System.currentTimeMillis(),
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