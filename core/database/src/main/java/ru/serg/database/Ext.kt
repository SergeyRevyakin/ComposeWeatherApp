package ru.serg.database

import ru.serg.database.room.CityWeather
import ru.serg.database.room.entity.AlertEntity
import ru.serg.database.room.entity.CityEntity
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity
import ru.serg.model.AirQuality
import ru.serg.model.AlertItem
import ru.serg.model.CityItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.WeatherItem


fun CityWeather.toWeatherItem() = WeatherItem(
    cityItem = cityEntity.toCityItem(),
    hourlyWeatherList = hourlyWeatherEntity.map { it.toHourlyWeather() },
    dailyWeatherList = dailyWeatherEntity.map { it.toDailyWeather() },
    alertList = alertEntityList.map { it.toAlertItem() }
)

fun CityItem.toCityEntity() = CityEntity(
    name,
    country,
    latitude,
    longitude,
    isFavorite,
    if (isFavorite) -1 else id,
    lastTimeUpdated = System.currentTimeMillis()
)

fun CityEntity.toCityItem() = CityItem(
    cityName,
    country.orEmpty(),
    latitude ?: 0.0,
    longitude ?: 0.0,
    isFavorite,
    id,
    lastTimeUpdated ?: 0
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
    airQuality = airQuality.orBlank(),
    precipitationProbability = precipitationProbability
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
    uvi = uvi.orZero(),
    precipitationProbability = precipitationProbability
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
    cityId = cityId,
    precipitationProbability = precipitationProbability
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
    cityId = cityId,
    precipitationProbability = precipitationProbability
)

fun AlertEntity.toAlertItem() = AlertItem(
    startAt = this.startAt,
    endAt = this.endAt,
    title = this.title,
    description = this.description
)

fun AlertItem.toAlertEntity(cityId: Int) = AlertEntity(
    startAt = this.startAt,
    endAt = this.endAt,
    title = this.title,
    description = this.description,
    cityId = cityId
)

fun Double?.orZero() = this ?: 0.0

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0

fun AirQuality?.orBlank() = this ?: AirQuality.blankAirQuality()
