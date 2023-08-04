@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.data.data_source.UpdatedLocalDataSource
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.common.NetworkResult
import ru.serg.composeweatherapp.utils.common.NetworkStatus
import ru.serg.composeweatherapp.utils.isNearTo
import ru.serg.composeweatherapp.utils.isSavedDataExpired
import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.model.UpdatedWeatherItem
import ru.serg.model.WeatherItem
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStoreDataSource: DataStoreDataSource,
    private val networkStatus: NetworkStatus,
    private val updatedLocalDataSource: UpdatedLocalDataSource
) {

    private lateinit var localCoordinates: Coordinates
    fun fetchCurrentLocationWeather(
        coordinates: Coordinates,
        forced: Boolean = false
    ): Flow<NetworkResult<WeatherItem>> =
        if (forced) {
            localCoordinates = coordinates
            fetchCoordinatesWeather(localCoordinates).flowOn(Dispatchers.IO)
        } else {
            localCoordinates = coordinates
            fetchWeather().flowOn(Dispatchers.IO)
        }

    fun fetchCityWeather(
        cityItem: CityItem,
        forced: Boolean = false
    ): Flow<NetworkResult<WeatherItem>> =
        if (forced) fetchWeather(cityItem).flowOn(Dispatchers.IO)
        else getLocalWeather(cityItem).flowOn(Dispatchers.IO)

    private fun getLocalWeather(
        cityItem: CityItem,
    ): Flow<NetworkResult<WeatherItem>> =
        localDataSource.getCurrentWeatherItem().flatMapLatest { list ->
            list.find {
                it.cityItem?.name == cityItem.name
            }?.let { item ->
                Log.d("getLocalWeather", "getLocalWeather: $item")
                dataStoreDataSource.fetchFrequency.flatMapMerge {
                    Log.d("getLocalWeather", "DataStore")
                    val delayInHours = Constants.HOUR_FREQUENCY_LIST[it]
                    if (isSavedDataExpired(item.lastUpdatedTime, delayInHours)) {
                        if (networkStatus.isNetworkConnected()) {
                            fetchWeather(cityItem)
                        } else {
                            flowOf(NetworkResult.Success(item))
                        }
                    } else {
                        flowOf(NetworkResult.Success(item))
                    }
                }
            } ?: if (networkStatus.isNetworkConnected()) {
                localDataSource.getCityHistorySearch().flatMapLatest {
                    if (it.contains(cityItem)) fetchWeather(cityItem)
                    else emptyFlow()
                }
            } else {
                noConnectionErrorFlow()
            }
        }

    private fun fetchWeather() =
        localDataSource.getFavouriteCityWithWeather().flatMapLatest { item ->
            if (item != null && item.cityItem?.latitude isNearTo localCoordinates.latitude &&
                item.cityItem?.longitude isNearTo localCoordinates.longitude
            ) {
                dataStoreDataSource.fetchFrequency.flatMapMerge {
                    Log.d("fetchWeather", "DataStore")
                    val delayInHours = Constants.HOUR_FREQUENCY_LIST[it]
                    if (isSavedDataExpired(item.lastUpdatedTime, delayInHours)) {
                        if (networkStatus.isNetworkConnected()) {
                            fetchCoordinatesWeather(localCoordinates)
                        } else {
                            flowOf(NetworkResult.Success(item))
                        }
                    } else {
                        flowOf(NetworkResult.Success(item))
                    }
                }
            } else {
                if (networkStatus.isNetworkConnected()) {
                    fetchCoordinatesWeather(localCoordinates)
                } else {
                    noConnectionErrorFlow()
                }
            }
        }

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

                    updatedLocalDataSource.saveWeather(oneCallResponse.data, cityItem)

                    localDataSource.insertCityItemToHistorySearch(cityItem)
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
                        updatedLocalDataSource.saveWeather(oneCallResponse.data, cityItem)
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

                        updatedLocalDataSource.saveWeather(oneCallResponse.data, cityItem)

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

    private fun noConnectionErrorFlow(): Flow<NetworkResult<WeatherItem>> = flowOf(
        NetworkResult.Error(
            message = "No Internet Connection",
        )
    )
}