package ru.serg.weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.serg.local.LocalDataSource
import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.model.UpdatedWeatherItem
import ru.serg.network.RemoteDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun fetchCurrentLocationWeather(
        coordinates: Coordinates,
    ): Flow<UpdatedWeatherItem> = combine(
        remoteDataSource.getWeather(coordinates.latitude, coordinates.longitude),
        remoteDataSource.getOneCallWeather(
            coordinates.latitude,
            coordinates.longitude
        )
    ) { weatherResponse, oneCallResponse ->

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

        UpdatedWeatherItem(
            cityItem,
            dailyWeather,
            hourlyWeather,
            oneCallResponse.alert?.description
        )

    }.flowOn(Dispatchers.IO)


    fun getCityWeatherFlow(
        cityItem: CityItem,
        isResultSavingRequired: Boolean = true
    ) = remoteDataSource.getOneCallWeather(cityItem.latitude, cityItem.longitude)
        .map { oneCallResponse ->

            val dailyWeather = oneCallResponse.daily?.map {
                DataMapper.mapDailyWeather(it)
            } ?: listOf()

            val hourlyWeather = oneCallResponse.hourly?.map {
                DataMapper.mapHourlyWeather(it)
            } ?: listOf()

            if (dailyWeather.isNotEmpty() && hourlyWeather.isNotEmpty() && isResultSavingRequired) {
                localDataSource.saveWeather(hourlyWeather, dailyWeather, cityItem)
            }

            UpdatedWeatherItem(
                cityItem,
                dailyWeather,
                hourlyWeather,
                oneCallResponse.alert?.description
            )

        }.flowOn(Dispatchers.IO)

    fun removeFavouriteCityParam(weatherItem: UpdatedWeatherItem) {
        localDataSource.saveWeather(
            hourlyWeatherList = weatherItem.hourlyWeatherList,
            dailyWeatherList = weatherItem.dailyWeatherList,
            cityItem = weatherItem.cityItem.copy(isFavorite = false)
        )
    }
}