package ru.serg.weather

import ru.serg.model.DailyTempItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.network.dto.AirQualityResponse
import ru.serg.network_weather_api.dto.visual_crossing.Day
import ru.serg.network_weather_api.dto.visual_crossing.Hour
import kotlin.math.roundToInt

object VisualCrossingMapper {

    fun mapHourlyWeather(
        hour: Hour,
        airQualityResponseItem: AirQualityResponse.AirQualityResponseItem?
    ) = HourlyWeather(
        windDirection = hour.winddir?.roundToInt().orZero(),
        windSpeed = (hour.windspeed.orZero() * 1000 / 60 / 60).roundedOrZero(),
        weatherDescription = hour.conditions.orEmpty(),
        weatherIcon = VisualCrossingIconMapper.map(hour.icon),
        dateTime = hour.datetimeEpoch.toTimeStamp(),
        humidity = hour.humidity?.roundToInt().orZero(),
        pressure = hour.pressure?.roundToInt().orZero(),
        currentTemp = hour.temp.orZero(),
        feelsLike = hour.feelslike.orZero(),
        uvi = hour.uvindex.orZero(),
        airQuality = DataMapper.mapAirQuality(airQualityResponseItem),
        precipitationProbability = hour.precipprob?.roundToInt().orZero(),
    )

    fun mapDailyWeather(daily: Day) = DailyWeather(
        windDirection = daily.winddir?.roundToInt().orZero(),
        windSpeed = (daily.windspeed.orZero() * 1000 / 60 / 60).roundedOrZero(),
        weatherDescription = daily.conditions.orEmpty(),
        weatherIcon = VisualCrossingIconMapper.map(daily.icon),
        dateTime = daily.datetimeEpoch.toTimeStamp(),
        humidity = daily.humidity?.roundToInt().orZero(),
        pressure = daily.pressure?.roundToInt().orZero(),
//        currentTemp = daily.temp.orZero(),
        uvi = daily.uvindex.orZero(),
//        airQuality = AirQuality.blankAirQuality(),
        precipitationProbability = daily.precipprob?.roundToInt().orZero(),
        feelsLike = DailyTempItem(
            maxTemp = daily.feelslikemax,
            minTemp = daily.feelslikemin,
            dayTemp = 0.0,
            morningTemp = 0.0,
            eveningTemp = 0.0,
            nightTemp = 0.0
        ),
        dailyWeatherItem = DailyTempItem(
            maxTemp = daily.tempmax,
            minTemp = daily.tempmin,
            dayTemp = 0.0,
            morningTemp = 0.0,
            eveningTemp = 0.0,
            nightTemp = 0.0
        ),
        sunset = daily.sunsetEpoch.toTimeStamp(),
        sunrise = daily.sunriseEpoch.toTimeStamp(),
    )
}