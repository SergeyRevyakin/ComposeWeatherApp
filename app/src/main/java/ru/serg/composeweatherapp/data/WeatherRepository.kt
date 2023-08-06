@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.utils.common.NetworkResult
import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.model.UpdatedWeatherItem
import ru.serg.model.WeatherItem
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun fetchCurrentLocationWeather(
        coordinates: Coordinates,
    ): Flow<NetworkResult<WeatherItem>> =
        fetchCoordinatesWeather(coordinates).flowOn(Dispatchers.IO)

    fun fetchCityWeather(
        cityItem: CityItem,
    ): Flow<NetworkResult<WeatherItem>> =
        fetchWeather(cityItem).flowOn(Dispatchers.IO)

    private fun fetchCoordinatesWeather(coordinates: Coordinates) = combine(
        remoteDataSource.getWeather(coordinates.latitude, coordinates.longitude),
        remoteDataSource.getOneCallWeather(
            coordinates.latitude,
            coordinates.longitude
        )
    ) { weatherResponse, oneCallResponse ->
        when {
            (weatherResponse is NetworkResult.Loading || oneCallResponse is NetworkResult.Loading) -> {
                NetworkResult.Loading()
            }

            (weatherResponse is NetworkResult.Error || oneCallResponse is NetworkResult.Error) -> {
                NetworkResult.Error(
                    weatherResponse.data?.message ?: oneCallResponse.data?.message
                )
            }

            (weatherResponse is NetworkResult.Success && oneCallResponse is NetworkResult.Success) -> {
                if (weatherResponse.data != null && oneCallResponse.data != null) {
                    val cityItem = DataMapper.mapCityItem(weatherResponse.data, true)

                    val weatherItem = DataMapper.getWeatherItem(
                        weatherResponse.data,
                        oneCallResponse.data,
                        cityItem
                    )

                    localDataSource.saveWeather(oneCallResponse.data, cityItem)

                    localDataSource.insertCityItemToSearchHistory(cityItem)
                    NetworkResult.Success(weatherItem)
                } else NetworkResult.Error(message = "No data!")

            }

            else -> NetworkResult.Loading()
        }
    }

    fun fetchWeather(
        cityItem: CityItem
    ): Flow<NetworkResult<WeatherItem>> =
        combine(
            remoteDataSource.getWeather(cityItem.latitude, cityItem.longitude),
            remoteDataSource.getOneCallWeather(cityItem.latitude, cityItem.longitude)
        ) { weatherResponse, oneCallResponse ->
            when {
                (weatherResponse is NetworkResult.Loading || oneCallResponse is NetworkResult.Loading) -> {
                    NetworkResult.Loading()
                }

                (weatherResponse is NetworkResult.Error || oneCallResponse is NetworkResult.Error) -> {
                    NetworkResult.Error(
                        weatherResponse.data?.message ?: oneCallResponse.data?.message
                    )
                }

                (weatherResponse is NetworkResult.Success && oneCallResponse is NetworkResult.Success) -> {

                    if (weatherResponse.data != null && oneCallResponse.data != null) {

                        val weatherItem = DataMapper.getWeatherItem(
                            weatherResponse.data,
                            oneCallResponse.data,
                            cityItem
                        )
                        localDataSource.saveWeather(oneCallResponse.data, cityItem)
                        NetworkResult.Success(weatherItem)

                    } else NetworkResult.Error(message = "No data!")
                }

                else -> NetworkResult.Loading()
            }
        }

    fun getCityWeatherFlow(
        cityItem: CityItem
    ) = remoteDataSource.getOneCallWeather(cityItem.latitude, cityItem.longitude)
        .mapLatest { oneCallResponse ->
            when {
                (oneCallResponse is NetworkResult.Loading) -> {
                    NetworkResult.Loading()
                }

                (oneCallResponse is NetworkResult.Error) -> {
                    NetworkResult.Error(
                        oneCallResponse.data?.message ?: "Unknown error"
                    )
                }

                (oneCallResponse is NetworkResult.Success) -> {

                    if (oneCallResponse.data != null) {

                        localDataSource.saveWeather(oneCallResponse.data, cityItem)

                        NetworkResult.Success(Any())

                    } else NetworkResult.Error(message = "No data!")
                }

                else -> NetworkResult.Loading()
            }
        }

    fun getCityWeatherNoSavingFlow(
        cityItem: CityItem
    ) = remoteDataSource.getOneCallWeather(cityItem.latitude, cityItem.longitude)
        .mapLatest { oneCallResponse ->
            when {
                (oneCallResponse is NetworkResult.Loading) -> {
                    NetworkResult.Loading()
                }

                (oneCallResponse is NetworkResult.Error) -> {
                    NetworkResult.Error(
                        oneCallResponse.data?.message ?: "Unknown error"
                    )
                }

                (oneCallResponse is NetworkResult.Success) -> {

                    if (oneCallResponse.data != null) {

                        val dailyWeather = oneCallResponse.data.daily?.map {
                            DataMapper.mapDailyWeather(it)
                        } ?: listOf()

                        val hourlyWeather = oneCallResponse.data.hourly?.map {
                            DataMapper.mapHourlyWeather(it)
                        } ?: listOf()

                        NetworkResult.Success(
                            UpdatedWeatherItem(
                                cityItem,
                                dailyWeather,
                                hourlyWeather
                            )
                        )

                    } else NetworkResult.Error(message = "No data!")
                }

                else -> NetworkResult.Loading()
            }
        }
}