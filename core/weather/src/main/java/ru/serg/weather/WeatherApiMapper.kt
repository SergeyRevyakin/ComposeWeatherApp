package ru.serg.weather

import ru.serg.model.AirQuality
import ru.serg.model.CityItem
import ru.serg.model.DailyTempItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.network.dto.OneCallResponse
import ru.serg.network_weather_api.dto.WeatherApiForecastResponse
import ru.serg.network_weather_api.dto.weather_api.Current
import ru.serg.network_weather_api.dto.weather_api.Forecastday
import ru.serg.network_weather_api.dto.weather_api.Hour
import kotlin.math.roundToInt

object WeatherApiMapper {
    fun mapCityItem(weatherResponse: WeatherApiForecastResponse, isFavourite: Boolean) =
        CityItem(
            name = weatherResponse.location?.name.orEmpty(),
            country = weatherResponse.location?.country.orEmpty(),
            latitude = weatherResponse.location?.lat ?: 0.0,
            longitude = weatherResponse.location?.lon ?: 0.0,
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
        hour: Hour,
    ) = HourlyWeather(
        windDirection = hour.windDegree.orZero(),
        windSpeed = hour.windKph.orZero(),
        weatherDescription = hour.condition?.text.orEmpty(),
        weatherIcon = IconMapper.map(hour.condition?.code),
        dateTime = hour.timeEpoch.toTimeStamp(),
        humidity = hour.humidity.orZero(),
        pressure = hour.pressureMb?.roundToInt().orZero(),
        currentTemp = hour.tempC.orZero(),
        feelsLike = hour.feelslikeC.orZero(),
        uvi = hour.uv.orZero(),
        airQuality = AirQuality(
            co = hour.airQuality?.co.orZero(),
            nh3 = 0.0,
            no2 = hour.airQuality?.no2.orZero(),
            o3 = hour.airQuality?.o3.orZero(),
            pm10 = hour.airQuality?.pm10.orZero(),
            pm25 = hour.airQuality?.pm25.orZero(),
            so2 = hour.airQuality?.so2.orZero(),
            owmIndex = 0,
            no = 0.0,
        ),
        precipitationProbability = maxOf(hour.chanceOfRain ?: 0, hour.chanceOfSnow ?: 0),
    )

    fun mapDailyWeather(daily: Forecastday) = DailyWeather(
        windDirection = 0,
        windSpeed = daily.day?.maxwindKph.orZero(),
        weatherDescription = daily.day?.condition?.text.orEmpty(),
        weatherIcon = IconMapper.map(daily.day?.condition?.code),
        dateTime = daily.dateEpoch.toTimeStamp(),
        humidity = daily.day?.avghumidity.orZero(),
        pressure = daily.hour?.firstOrNull()?.pressureMb?.roundToInt().orZero(),
        feelsLike = DailyTempItem(
            maxTemp = daily.day?.maxtempC,
            minTemp = daily.day?.mintempC,
            dayTemp = 0.0,
            morningTemp = 0.0,
            eveningTemp = 0.0,
            nightTemp = 0.0
        ),
        dailyWeatherItem = DailyTempItem(
            maxTemp = daily.day?.maxtempC,
            minTemp = daily.day?.mintempC,
            dayTemp = 0.0,
            morningTemp = 0.0,
            eveningTemp = 0.0,
            nightTemp = 0.0
        ),
        sunset = 0.toTimeStamp(),
        sunrise = 0.toTimeStamp(),
        uvi = daily.day?.uv.orZero(),
        precipitationProbability = maxOf(
            daily.day?.dailyChanceOfRain ?: 0,
            daily.day?.dailyChanceOfSnow ?: 0
        )
    )

    fun mapCurrentWeather(current: Current) = HourlyWeather(
        windSpeed = current.windKph.orZero(),
        windDirection = current.windDegree.orZero(),
        feelsLike = current.feelslikeC.orZero(),
        currentTemp = current.tempC.orZero(),
        humidity = current.humidity.orZero(),
        pressure = current.pressureMb?.roundToInt().orZero(),
        weatherDescription = current.condition?.text.orEmpty(),
        weatherIcon = IconMapper.map(current.condition?.code),
        dateTime = System.currentTimeMillis(),
        uvi = current.uv.orZero(),
        airQuality = AirQuality(
            co = current.airQuality?.co.orZero(),
            nh3 = 0.0,
            no2 = current.airQuality?.no2.orZero(),
            o3 = current.airQuality?.o3.orZero(),
            pm10 = current.airQuality?.pm10.orZero(),
            pm25 = current.airQuality?.pm25.orZero(),
            so2 = current.airQuality?.so2.orZero(),
            owmIndex = 0,
            no = 0.0,
        ),
        precipitationProbability = 0,
    )
}