package ru.serg.weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import ru.serg.local.LocalDataSource
import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.model.WeatherItem
import ru.serg.network.RemoteDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun fetchCurrentLocationWeather(
        coordinates: Coordinates,
    ): Flow<WeatherItem> = combine(
        remoteDataSource.getWeather(coordinates.latitude, coordinates.longitude),
        remoteDataSource.getOneCallWeather(
            coordinates.latitude,
            coordinates.longitude
        ),
        remoteDataSource.getAirQuality(coordinates.latitude, coordinates.longitude)
    ) { weatherResponse, oneCallResponse, airQualityResponse ->

        val cityItem = DataMapper.mapCityItem(weatherResponse, true)

        val dailyWeather = oneCallResponse.daily?.map {
            DataMapper.mapDailyWeather(it)
        } ?: listOf()

        val hourlyWeather = oneCallResponse.hourly?.map {
            DataMapper.mapHourlyWeather(it)
        } ?: listOf()

        if (dailyWeather.isNotEmpty() && hourlyWeather.isNotEmpty()) {
            localDataSource.saveWeather(hourlyWeather, dailyWeather, cityItem)
            localDataSource.insertCityItemToSearchHistory(cityItem)
        }

        WeatherItem(
            cityItem,
            dailyWeather,
            hourlyWeather,
            oneCallResponse.alert?.description
        )

    }.flowOn(Dispatchers.IO)


    fun getCityWeatherFlow(
        cityItem: CityItem,
        isResultSavingRequired: Boolean = true
    ) = combine(
        remoteDataSource.getOneCallWeather(cityItem.latitude, cityItem.longitude),
        remoteDataSource.getAirQuality(cityItem.latitude, cityItem.longitude)
    ) { oneCallResponse, airQualityResponse ->

            val dailyWeather = oneCallResponse.daily?.map {
                DataMapper.mapDailyWeather(it)
            } ?: listOf()

            val hourlyWeather = oneCallResponse.hourly?.map {
                DataMapper.mapHourlyWeather(it)
            } ?: listOf()

            if (dailyWeather.isNotEmpty() && hourlyWeather.isNotEmpty() && isResultSavingRequired) {
                localDataSource.saveWeather(hourlyWeather, dailyWeather, cityItem)
            }

        WeatherItem(
                cityItem,
                dailyWeather,
                hourlyWeather,
                oneCallResponse.alert?.description
            )

        }.flowOn(Dispatchers.IO)

    fun removeFavouriteCityParam(weatherItem: WeatherItem) {
        localDataSource.saveWeather(
            hourlyWeatherList = weatherItem.hourlyWeatherList,
            dailyWeatherList = weatherItem.dailyWeatherList,
            cityItem = weatherItem.cityItem.copy(isFavorite = false)
        )
    }
}