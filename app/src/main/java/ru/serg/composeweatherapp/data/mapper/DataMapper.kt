package ru.serg.composeweatherapp.data.mapper

import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.data.dto.DailyWeather
import ru.serg.composeweatherapp.data.dto.DayWeatherItem
import ru.serg.composeweatherapp.data.dto.HourWeatherItem
import ru.serg.composeweatherapp.data.dto.HourlyWeather
import ru.serg.composeweatherapp.data.dto.IntraDayTempItem
import ru.serg.composeweatherapp.data.dto.UpdatedDailyTempItem
import ru.serg.composeweatherapp.data.dto.WeatherItem
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.utils.weather_mapper.IconMapper
import ru.serg.composeweatherapp.utils.orEmpty
import ru.serg.composeweatherapp.utils.orZero
import ru.serg.composeweatherapp.utils.toTimeStamp

object DataMapper {
    fun mapCityItem(weatherResponse: WeatherResponse, isFavourite: Boolean) =
        CityItem(
            name = weatherResponse.name.orEmpty(),
            country = weatherResponse.sys?.country.orEmpty(),
            latitude = weatherResponse.coord?.lat ?: 0.0,
            longitude = weatherResponse.coord?.lon ?: 0.0,
            isFavourite,
            id = if (isFavourite) -1 else 0,
            getTimeMillis()
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

        return mapWeatherItem(
            cityItem,
            dailyList,
            hourlyList,
            weatherResponse,
            oneCallResponse.alert?.description
        )
    }

    fun getUpdatedDailyTempItem(daily: OneCallResponse.Daily) = UpdatedDailyTempItem(
        morningTemp = daily.temp?.morn.orZero(),
        dayTemp = daily.temp?.day.orZero(),
        eveningTemp = daily.temp?.eve.orZero(),
        nightTemp = daily.temp?.night.orZero(),
        maxTemp = daily.temp?.max.orZero(),
        minTemp = daily.temp?.min.orZero()
    )

    fun getFeelsLikeDailyTempItem(daily: OneCallResponse.Daily) = UpdatedDailyTempItem(
        morningTemp = daily.feelsLike?.morn.orZero(),
        dayTemp = daily.feelsLike?.day.orZero(),
        eveningTemp = daily.feelsLike?.eve.orZero(),
        nightTemp = daily.feelsLike?.night.orZero(),
        maxTemp = null,
        minTemp = null
    )

    fun mapHourlyWeather(hourly: OneCallResponse.Hourly) = HourlyWeather(
        windDirection = hourly.windDeg.orZero(),
        windSpeed = hourly.windSpeed.orZero(),
        weatherDescription = hourly.weather?.first()?.description.orEmpty(),
        weatherIcon = IconMapper.map(hourly.weather?.first()?.id),
        dateTime = hourly.dt.toTimeStamp(),
        humidity = hourly.humidity.orZero(),
        pressure = hourly.pressure.orZero(),
        currentTemp = hourly.temp.orZero(),
        feelsLike = hourly.feelsLike.orZero(),
        uvi = hourly.uvi.orZero()
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

}