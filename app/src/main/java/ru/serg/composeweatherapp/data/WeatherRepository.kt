@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import io.ktor.util.date.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.Ext.isNearTo
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.NetworkStatus
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStoreDataSource: DataStoreDataSource,
    private val networkStatus: NetworkStatus
) {

    suspend fun fetchCurrentLocationWeather(
        coordinatesWrapper: CoordinatesWrapper,
        forced: Boolean = false
    ): Flow<NetworkResult<WeatherItem>> = if (forced) fetchCoordinatesWeather(
        coordinatesWrapper
    )
    else fetchWeather(coordinatesWrapper)

    suspend fun fetchCityWeather(
        cityItem: CityItem,
        forced: Boolean = false
    ): Flow<NetworkResult<WeatherItem>> = if (forced) fetchWeather(cityItem)
    else getLocalWeather(cityItem)

    private suspend fun getLocalWeather(
        cityItem: CityItem,
    ): Flow<NetworkResult<WeatherItem>> =
        localDataSource.getCurrentWeatherItem().flatMapLatest { list ->
            list.find {
                it.cityItem?.name == cityItem.name
            }?.let { item ->
                dataStoreDataSource.fetchFrequency.flatMapLatest {
                    val delayInHours = Constants.HOUR_FREQUENCY_LIST[it]
                    if (item.lastUpdatedTime > (getTimeMillis() - (delayInHours * 60L * 60L * 1000L))) {
                        flowOf(NetworkResult.Success(item))
                    } else {
                        if (networkStatus.isNetworkConnected()) {
                            fetchWeather(cityItem)
                        } else {
                            flowOf(NetworkResult.Success(item))
                        }

                    }
                }
            } ?: if (networkStatus.isNetworkConnected()) {
                localDataSource.getCityHistorySearch().flatMapLatest {
                    if (it.contains(cityItem)) fetchWeather(cityItem)
                    else emptyFlow()
                }

            } else {
                flowOf(
                    NetworkResult.Error(
                        message = "No Internet Connection",
                        errorTextResource = R.string.no_connection
                    )
                )
            }
        }

    private suspend fun fetchWeather(
        coordinatesWrapper: CoordinatesWrapper
    ): Flow<NetworkResult<WeatherItem>> =
        localDataSource.getCurrentWeatherItem().flatMapLatest { list ->

            list.find {
                it.cityItem?.latitude isNearTo coordinatesWrapper.latitude &&
                        it.cityItem?.longitude isNearTo coordinatesWrapper.longitude
            }?.let { item ->
                dataStoreDataSource.fetchFrequency.flatMapLatest {
                    val delayInHours = Constants.HOUR_FREQUENCY_LIST[it]
                    if (item.lastUpdatedTime > (getTimeMillis() - (delayInHours * 60L * 60L * 1000L))) {
                        flowOf(NetworkResult.Success(item))
                    } else {
                        if (networkStatus.isNetworkConnected()) {
                            fetchCoordinatesWeather(coordinatesWrapper)
                        } else {
                            flowOf(NetworkResult.Success(item))
                        }
                    }
                }
            } ?: if (networkStatus.isNetworkConnected()) {
                fetchCoordinatesWeather(coordinatesWrapper)
            } else {
                flowOf(
                    NetworkResult.Error(
                        message = "No Internet Connection",
                        errorTextResource = R.string.no_connection
                    )
                )
            }
        }

    private suspend fun fetchCoordinatesWeather(coordinatesWrapper: CoordinatesWrapper) = combine(
        remoteDataSource.getWeather(coordinatesWrapper.latitude, coordinatesWrapper.longitude),
        remoteDataSource.getOneCallWeather(
            coordinatesWrapper.latitude,
            coordinatesWrapper.longitude
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

                    localDataSource.insertCityItemToHistorySearch(cityItem)
                    localDataSource.saveWeather(weatherItem)
                    NetworkResult.Success(weatherItem)
                } else NetworkResult.Error(message = "No data!")

            }

            else -> NetworkResult.Loading()
        }
    }

    suspend fun fetchWeather(
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

                        localDataSource.saveWeather(weatherItem)
                        NetworkResult.Success(weatherItem)

                    } else NetworkResult.Error(message = "No data!")
                }

                else -> NetworkResult.Loading()
            }
        }
}