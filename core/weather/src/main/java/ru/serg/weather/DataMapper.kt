package ru.serg.weather

import ru.serg.model.AirQuality
import ru.serg.model.CityItem
import ru.serg.model.DailyTempItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.network.dto.AirQualityResponse
import ru.serg.network.dto.OneCallResponse
import ru.serg.network.dto.WeatherResponse

object DataMapper {
    fun mapCityItem(weatherResponse: WeatherResponse, isFavourite: Boolean) =
        CityItem(
            name = weatherResponse.name.orEmpty(),
            country = weatherResponse.sys?.country.orEmpty(),
            latitude = weatherResponse.coord?.lat ?: 0.0,
            longitude = weatherResponse.coord?.lon ?: 0.0,
            isFavourite,
            id = if (isFavourite) -1 else 0,
            System.currentTimeMillis()
        )

    private fun getUpdatedDailyTempItem(daily: OneCallResponse.Daily) = DailyTempItem(
        morningTemp = daily.temp?.morn.orZero(),
        dayTemp = daily.temp?.day.orZero(),
        eveningTemp = daily.temp?.eve.orZero(),
        nightTemp = daily.temp?.night.orZero(),
        maxTemp = daily.temp?.max.orZero(),
        minTemp = daily.temp?.min.orZero()
    )

    private fun getFeelsLikeDailyTempItem(daily: OneCallResponse.Daily) =
        DailyTempItem(
            morningTemp = daily.feelsLike?.morn.orZero(),
            dayTemp = daily.feelsLike?.day.orZero(),
            eveningTemp = daily.feelsLike?.eve.orZero(),
            nightTemp = daily.feelsLike?.night.orZero(),
            maxTemp = null,
            minTemp = null
        )

    fun mapHourlyWeather(
        hourly: OneCallResponse.Hourly,
        airQualityResponseItem: AirQualityResponse.AirQualityResponseItem?
    ) = HourlyWeather(
        windDirection = hourly.windDeg.orZero(),
        windSpeed = hourly.windSpeed.orZero(),
        weatherDescription = hourly.weather?.first()?.description.orEmpty(),
        weatherIcon = IconMapper.map(hourly.weather?.first()?.id),
        dateTime = hourly.dt.toTimeStamp(),
        humidity = hourly.humidity.orZero(),
        pressure = hourly.pressure.orZero(),
        currentTemp = hourly.temp.orZero(),
        feelsLike = hourly.feelsLike.orZero(),
        uvi = hourly.uvi.orZero(),
        airQuality = mapAirQuality(airQualityResponseItem)
    )

    fun mapDailyWeather(daily: OneCallResponse.Daily) = DailyWeather(
        windDirection = daily.windDeg.orZero(),
        windSpeed = daily.windSpeed.orZero(),
        weatherDescription = daily.weather?.first()?.description.orEmpty(),
        weatherIcon = IconMapper.map(daily.weather?.first()?.id),
        dateTime = daily.dt.toTimeStamp(),
        humidity = daily.humidity.orZero(),
        pressure = daily.pressure.orZero(),
        feelsLike = getFeelsLikeDailyTempItem(daily),
        dailyWeatherItem = getUpdatedDailyTempItem(daily),
        sunset = daily.sunset.toTimeStamp(),
        sunrise = daily.sunrise.toTimeStamp(),
        uvi = daily.uvi.orZero()
    )

    private fun mapAirQuality(responseItem: AirQualityResponse.AirQualityResponseItem?): AirQuality {
        return if (responseItem == null) AirQuality.blankAirQuality()
        else {
            AirQuality(
                co = responseItem.components?.co.orZero(),
                nh3 = responseItem.components?.nh3.orZero(),
                no = responseItem.components?.no.orZero(),
                no2 = responseItem.components?.no2.orZero(),
                o3 = responseItem.components?.o3.orZero(),
                pm10 = responseItem.components?.pm10.orZero(),
                pm25 = responseItem.components?.pm25.orZero(),
                so2 = responseItem.components?.so2.orZero(),
                owmIndex = responseItem.airQualityIndex?.aqi ?: 0,
            )
        }
    }
}