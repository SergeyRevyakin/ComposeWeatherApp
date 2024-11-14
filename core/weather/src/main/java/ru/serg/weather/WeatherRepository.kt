package ru.serg.weather

import androidx.compose.ui.util.fastFlatMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import ru.serg.local.LocalDataSource
import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.model.WeatherItem
import ru.serg.network.RemoteDataSource
import ru.serg.network.dto.AirQualityResponse
import ru.serg.network_weather_api.VisualCrossingRemoteDataSource
import ru.serg.network_weather_api.dto.VisualCrossingResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val visualCrossingDataSource: VisualCrossingRemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun fetchCurrentLocationWeather(
        coordinates: Coordinates,
    ): Flow<WeatherItem> = combine(
        remoteDataSource.getWeather(coordinates.latitude, coordinates.longitude),
        remoteDataSource.getAirQuality(coordinates.latitude, coordinates.longitude),
        visualCrossingDataSource.getVisualCrossingForecast(
            coordinates.latitude,
            coordinates.longitude
        )
    ) { weatherResponse, airQuality, visualCrossingResponse ->

        val cityItem = DataMapper.mapCityItem(weatherResponse, true)

        val hourlyWeather = mapHours(visualCrossingResponse, airQuality)

        val dailyWeather = mapDays(visualCrossingResponse)

        val alerts = mapAlerts(visualCrossingResponse)

        if (dailyWeather.isNotEmpty() && hourlyWeather.isNotEmpty()) {
            localDataSource.saveWeather(hourlyWeather, dailyWeather, alerts, cityItem)
            localDataSource.insertCityItemToSearchHistory(cityItem)
        }

        WeatherItem(
            cityItem,
            dailyWeather,
            hourlyWeather,
            alerts,
            visualCrossingResponse.alerts?.firstOrNull()?.description
        )
    }.flowOn(Dispatchers.IO)


    fun getCityWeatherFlow(
        cityItem: CityItem,
        isResultSavingRequired: Boolean = true
    ) = combine(
        visualCrossingDataSource.getVisualCrossingForecast(cityItem.latitude, cityItem.longitude),
        remoteDataSource.getAirQuality(cityItem.latitude, cityItem.longitude)
    ) { visualCrossingResponse, airQuality ->

        val hourlyWeather = mapHours(visualCrossingResponse, airQuality)

        val dailyWeather = mapDays(visualCrossingResponse)

        val updatedCityItem = cityItem.copy(lastTimeUpdated = System.currentTimeMillis())

        val alerts = mapAlerts(visualCrossingResponse)

        if (dailyWeather.isNotEmpty() && hourlyWeather.isNotEmpty() && isResultSavingRequired) {
            localDataSource.saveWeather(hourlyWeather, dailyWeather, alerts, updatedCityItem)
        }

        WeatherItem(
            updatedCityItem,
            dailyWeather,
            hourlyWeather,
            alerts,
            visualCrossingResponse.alerts?.firstOrNull()?.description
        )
    }.flowOn(Dispatchers.IO)

    fun removeFavouriteCityParam(weatherItem: WeatherItem) {
        localDataSource.saveWeather(
            hourlyWeatherList = weatherItem.hourlyWeatherList,
            dailyWeatherList = weatherItem.dailyWeatherList,
            alertList = weatherItem.alertList,
            cityItem = weatherItem.cityItem.copy(isFavorite = false)
        )
    }

    private fun mapHours(
        visualCrossingResponse: VisualCrossingResponse,
        airQuality: AirQualityResponse
    ) =
        visualCrossingResponse.days?.mapNotNull { it }?.fastFlatMap { it.hours ?: emptyList() }
            ?.map { hour ->
                hour.let { hourly ->
                    val airQualityResponseItem =
                        airQuality.list.firstOrNull { it.timestamp.toTimeStamp() == hourly.datetimeEpoch.toTimeStamp() }
                    VisualCrossingMapper.mapHourlyWeather(hourly, airQualityResponseItem)
                }
            }?.filter {
                it.dateTime > System.currentTimeMillis()
            } ?: listOf()

    private fun mapDays(visualCrossingResponse: VisualCrossingResponse) =
        visualCrossingResponse.days?.mapNotNull {
            it?.let {
                VisualCrossingMapper.mapDailyWeather(
                    it
                )
            }
        }?.filter {
            it.dateTime > System.currentTimeMillis()
        } ?: listOf()

    private fun mapAlerts(visualCrossingResponse: VisualCrossingResponse) =
        visualCrossingResponse.alerts?.map {
            VisualCrossingMapper.mapAlert(it)
        } ?: emptyList()
}