package ru.serg.weather

import com.serg.network_self_proxy.dto.AirPollutionModel
import com.serg.network_self_proxy.dto.DailyWeatherModel
import com.serg.network_self_proxy.dto.HourlyWeatherModel
import com.serg.network_self_proxy.dto.WeatherResponse
import ru.serg.model.AirQuality
import ru.serg.model.CityItem
import ru.serg.model.DailyTempItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather

object SelfDataMapper {
    fun mapCityItem(weatherResponse: WeatherResponse, isFavourite: Boolean) =
        CityItem(
            name = weatherResponse.city?.name.orEmpty(),
            country = weatherResponse.city?.county.orEmpty(),
            latitude = weatherResponse.city?.latitude ?: 0.0,
            longitude = weatherResponse.city?.longitude ?: 0.0,
            secondsOffset = weatherResponse.city?.secondsOffset ?: 0L,
            isFavourite,
            id = if (isFavourite) -1 else 0,
            System.currentTimeMillis()
        )

    private fun getUpdatedDailyTempItem(daily: DailyWeatherModel) = DailyTempItem(
        morningTemp = 0.0,
        dayTemp = 0.0,
        eveningTemp = 0.0,
        nightTemp = 0.0,
        maxTemp = daily.maxTemp,
        minTemp = daily.minTemp
    )

    private fun getFeelsLikeDailyTempItem(daily: DailyWeatherModel) =
        DailyTempItem(
            morningTemp = 0.0,
            dayTemp = 0.0,
            eveningTemp = 0.0,
            nightTemp = 0.0,
            maxTemp = daily.feelsLikeMaxTemp,
            minTemp = daily.feelsLikeMinTemp
        )

    fun mapHourlyWeather(
        hourly: HourlyWeatherModel,
        airQualityResponseItem: AirPollutionModel?
    ) = HourlyWeather(
        windDirection = hourly.windDirection.orZero(),
        windSpeed = hourly.windSpeed.orZero(),
        weatherDescription = hourly.weatherDescription.orEmpty(),
        weatherIcon = IconMapper.map(hourly.weatherIcon),
        dateTime = hourly.dateTime.toTimeStamp(),
        humidity = hourly.humidity.orZero(),
        pressure = hourly.pressure.orZero(),
        currentTemp = hourly.currentTemp.orZero(),
        feelsLike = hourly.feelsLike.orZero(),
        uvi = hourly.uvi.orZero(),
        airQuality = mapAirQuality(airQualityResponseItem),
        precipitationProbability = hourly.precipitationProbability.orZero()
    )

    fun mapDailyWeather(daily: DailyWeatherModel) = DailyWeather(
        windDirection = daily.windDirection.orZero(),
        windSpeed = daily.windSpeed.orZero(),
        weatherDescription = daily.weatherDescription.orEmpty(),
        weatherIcon = IconMapper.map(daily.weatherIcon),
        dateTime = daily.dateTime.toTimeStamp(),
        humidity = daily.humidity.orZero(),
        pressure = daily.pressure.orZero(),
        feelsLike = getFeelsLikeDailyTempItem(daily),
        dailyWeatherItem = getUpdatedDailyTempItem(daily),
        sunset = daily.sunset.toTimeStamp(),
        sunrise = daily.sunrise.toTimeStamp(),
        uvi = daily.uvi.orZero(),
        precipitationProbability = daily.precipitationProbability.orZero()
    )

    private fun mapAirQuality(responseItem: AirPollutionModel?): AirQuality {
        return if (responseItem == null) AirQuality.blankAirQuality()
        else {
            AirQuality(
                co = responseItem.co.orZero(),
                nh3 = responseItem.nh3.orZero(),
                no = responseItem.no.orZero(),
                no2 = responseItem.no2.orZero(),
                o3 = responseItem.o3.orZero(),
                pm10 = responseItem.pm10.orZero(),
                pm25 = responseItem.pm25.orZero(),
                so2 = responseItem.so2.orZero(),
                owmIndex = 0,
            )
        }
    }
}