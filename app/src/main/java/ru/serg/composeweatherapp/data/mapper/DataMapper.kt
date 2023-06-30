package ru.serg.composeweatherapp.data.mapper

import io.ktor.util.date.*
import ru.serg.composeweatherapp.data.dto.*
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.utils.IconMapper

object DataMapper {
    fun mapCityItem(weatherResponse: WeatherResponse, isFavourite: Boolean) =
        CityItem(
            name = weatherResponse.name.orEmpty(),
            country = weatherResponse.sys?.country.orEmpty(),
            latitude = weatherResponse.coord?.lat ?: 0.0,
            longitude = weatherResponse.coord?.lon ?: 0.0,
            isFavourite
        )

    private fun mapDayWeatherItem(
        feelsLikeIntraDay: IntraDayTempItem,
        tempIntraDay: IntraDayTempItem,
        daily: OneCallResponse.Daily
    ) = DayWeatherItem(
        feelsLike = feelsLikeIntraDay,
        temp = tempIntraDay,
        windSpeed = daily.windSpeed ?: 0.0,
        windDirection = daily.windDeg ?: 0,
        humidity = daily.humidity ?: 0,
        pressure = daily.pressure ?: 0,
        weatherDescription = daily.weather?.first()?.description.orEmpty(),
        weatherIcon = IconMapper.map(daily.weather?.first()?.id),
        dateTime = (daily.dt ?: 0) * 1000L,
        sunrise = (daily.sunrise?.toLong() ?: 0L) * 1000L,
        sunset = (daily.sunset?.toLong() ?: 0L) * 1000L
    )

    private fun mapHourWeatherItem(hourly: OneCallResponse.Hourly) = HourWeatherItem(
        weatherIcon = IconMapper.map(hourly.weather?.first()?.id),
        currentTemp = hourly.feelsLike ?: 0.0,
        timestamp = (hourly.dt ?: 0) * 1000L
    )

    private fun mapWeatherItem(
        cityItem: CityItem,
        dailyList: List<DayWeatherItem>,
        hourlyList: List<HourWeatherItem>,
        weatherResponse: WeatherResponse,
        alert: String? = null
    ) = WeatherItem(
        feelsLike = weatherResponse.main?.feelsLike,
        currentTemp = weatherResponse.main?.temp,
        windSpeed = weatherResponse.wind?.speed,
        windDirection = weatherResponse.wind?.deg,
        humidity = weatherResponse.main?.humidity,
        pressure = weatherResponse.main?.pressure,
        weatherDescription = weatherResponse.weather?.first()?.description,
        weatherIcon = IconMapper.map(weatherResponse.weather?.first()?.id),
        dateTime = (weatherResponse.dt?.toLong() ?: 0) * 1000L,
        cityItem = cityItem,
        lastUpdatedTime = getTimeMillis(),
        dailyWeatherList = dailyList,
        hourlyWeatherList = hourlyList,
        alertMessage = alert
    )

    fun getWeatherItem(
        weatherResponse: WeatherResponse,
        oneCallResponse: OneCallResponse,
        cityItem: CityItem
    ): WeatherItem {
        val hourlyList = oneCallResponse.hourly?.map { hourly ->
            mapHourWeatherItem(hourly)
        } ?: listOf()

        val dailyList = oneCallResponse.daily?.map { daily ->
            val feelsLikeIntraDay = IntraDayTempItem(
                morningTemp = daily.feelsLike?.morn ?: 0.0,
                dayTemp = daily.feelsLike?.day ?: 0.0,
                eveningTemp = daily.feelsLike?.eve ?: 0.0,
                nightTemp = daily.feelsLike?.night ?: 0.0,
            )

            val tempIntraDay = IntraDayTempItem(
                morningTemp = daily.temp?.morn ?: 0.0,
                dayTemp = daily.temp?.day ?: 0.0,
                eveningTemp = daily.temp?.eve ?: 0.0,
                nightTemp = daily.temp?.night ?: 0.0,
            )

            mapDayWeatherItem(feelsLikeIntraDay, tempIntraDay, daily)
        } ?: emptyList()

        return mapWeatherItem(cityItem, dailyList, hourlyList, weatherResponse, oneCallResponse.alert?.description)
    }
}