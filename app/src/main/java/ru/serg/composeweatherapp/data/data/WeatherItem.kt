package ru.serg.composeweatherapp.data.data

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
)