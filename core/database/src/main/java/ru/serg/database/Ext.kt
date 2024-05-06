package ru.serg.database

import ru.serg.database.room.entity.CityWeather
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity
import ru.serg.database.room.entity.toCityItem
import ru.serg.model.AirQuality
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.WeatherItem


fun CityWeather.toWeatherItem() = WeatherItem(
    cityItem = cityEntity.toCityItem(),
    hourlyWeatherList = hourlyWeatherEntity.map {
        it.toHourlyWeather()
    },
    dailyWeatherList = dailyWeatherEntity.map {
        it.toDailyWeather()
    }
)

fun HourlyWeatherEntity.toHourlyWeather() = HourlyWeather(
    windSpeed = windSpeed.orZero(),
    windDirection = windDirection.orZero(),
    weatherIcon = weatherIcon.orZero(),
    weatherDescription = weatherDescription.orEmpty(),
    feelsLike = feelsLike.orZero(),
    currentTemp = currentTemp.orZero(),
    humidity = humidity.orZero(),
    pressure = pressure.orZero(),
    dateTime = dateTime.orZero(),
    uvi = uvi.orZero(),
    airQuality = airQuality.orBlank()
)

fun DailyWeatherEntity.toDailyWeather() = DailyWeather(
    windSpeed = windSpeed.orZero(),
    windDirection = windDirection.orZero(),
    weatherIcon = weatherIcon.orZero(),
    weatherDescription = weatherDescription.orEmpty(),
    humidity = humidity.orZero(),
    pressure = pressure.orZero(),
    dateTime = dateTime.orZero(),
    feelsLike = feelsLike,
    dailyWeatherItem = dailyWeatherItem,
    sunrise = sunrise.orZero(),
    sunset = sunset.orZero(),
    uvi = uvi.orZero()
)

fun HourlyWeather.toHourlyWeatherEntity(cityId: Int) = HourlyWeatherEntity(
    windSpeed = windSpeed.orZero(),
    windDirection = windDirection.orZero(),
    weatherIcon = weatherIcon.orZero(),
    weatherDescription = weatherDescription,
    feelsLike = feelsLike.orZero(),
    currentTemp = currentTemp.orZero(),
    humidity = humidity.orZero(),
    pressure = pressure.orZero(),
    dateTime = dateTime.orZero(),
    uvi = uvi.orZero(),
    airQuality = airQuality,
    cityId = cityId
)

fun DailyWeather.toDailyWeatherEntity(cityId: Int) = DailyWeatherEntity(
    windSpeed = windSpeed.orZero(),
    windDirection = windDirection.orZero(),
    weatherIcon = weatherIcon.orZero(),
    weatherDescription = weatherDescription,
    humidity = humidity.orZero(),
    pressure = pressure.orZero(),
    dateTime = dateTime.orZero(),
    feelsLike = feelsLike,
    dailyWeatherItem = dailyWeatherItem,
    sunrise = sunrise.orZero(),
    sunset = sunset.orZero(),
    uvi = uvi.orZero(),
    cityId = cityId
)


fun Double?.orZero() = this ?: 0.0

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0

fun AirQuality?.orBlank() = this ?: AirQuality.blankAirQuality()
