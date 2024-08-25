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

        if (weatherResponse.message != null || oneCallResponse.message != null || oneCallResponse.hourly.isNullOrEmpty()
            || oneCallResponse.daily.isNullOrEmpty()
        ) throw Exception(oneCallResponse.message ?: weatherResponse.message)

        val dailyWeather = oneCallResponse.daily?.map {
            DataMapper.mapDailyWeather(it)
        } ?: listOf()

        val hourlyWeather = oneCallResponse.hourly?.map { hourly ->
            val airQualityResponseItem =
                airQualityResponse.list.firstOrNull { it.timestamp == hourly.dt }
            DataMapper.mapHourlyWeather(hourly, airQualityResponseItem)
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

        if (oneCallResponse.message != null || oneCallResponse.hourly.isNullOrEmpty()
            || oneCallResponse.daily.isNullOrEmpty()
        ) throw Exception(oneCallResponse.message)

        val dailyWeather = oneCallResponse.daily?.map {
            DataMapper.mapDailyWeather(it)
        } ?: listOf()

        val hourlyWeather = oneCallResponse.hourly?.map { hourly ->
            val airQualityResponseItem =
                airQualityResponse.list.firstOrNull { it.timestamp == hourly.dt }
            DataMapper.mapHourlyWeather(hourly, airQualityResponseItem)
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