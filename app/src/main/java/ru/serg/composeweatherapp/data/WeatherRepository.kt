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
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.data.dto.CoordinatesWrapper
import ru.serg.composeweatherapp.data.dto.DailyWeather
import ru.serg.composeweatherapp.data.dto.HourlyWeather
import ru.serg.composeweatherapp.data.dto.UpdatedWeatherItem
import ru.serg.composeweatherapp.data.dto.WeatherItem
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.IconMapper
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.NetworkStatus
import ru.serg.composeweatherapp.utils.isNearTo
import ru.serg.composeweatherapp.utils.isSavedDataExpired
import ru.serg.composeweatherapp.utils.orEmpty
import ru.serg.composeweatherapp.utils.orZero
import ru.serg.composeweatherapp.utils.toTimeStamp
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStoreDataSource: DataStoreDataSource,
    private val networkStatus: NetworkStatus,
    private val updatedLocalDataSource: UpdatedLocalDataSource
) {

    private lateinit var localCoordinatesWrapper: CoordinatesWrapper
    fun fetchCurrentLocationWeather(
        coordinatesWrapper: CoordinatesWrapper,
        forced: Boolean = false
    ): Flow<NetworkResult<WeatherItem>> =
        if (forced) {
            localCoordinatesWrapper = coordinatesWrapper
            fetchCoordinatesWeather(localCoordinatesWrapper).flowOn(Dispatchers.IO)
        } else {
            localCoordinatesWrapper = coordinatesWrapper
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
            if (item != null && item.cityItem?.latitude isNearTo localCoordinatesWrapper.latitude &&
                item.cityItem?.longitude isNearTo localCoordinatesWrapper.longitude
            ) {
                dataStoreDataSource.fetchFrequency.flatMapMerge {
                    Log.d("fetchWeather", "DataStore")
                    val delayInHours = Constants.HOUR_FREQUENCY_LIST[it]
                    if (isSavedDataExpired(item.lastUpdatedTime, delayInHours)) {
                        if (networkStatus.isNetworkConnected()) {
                            fetchCoordinatesWeather(localCoordinatesWrapper)
                        } else {
                            flowOf(NetworkResult.Success(item))
                        }
                    } else {
                        flowOf(NetworkResult.Success(item))
                    }
                }
            } else {
                if (networkStatus.isNetworkConnected()) {
                    fetchCoordinatesWeather(localCoordinatesWrapper)
                } else {
                    noConnectionErrorFlow()
                }
            }
        }

    private fun fetchCoordinatesWeather(coordinatesWrapper: CoordinatesWrapper) = combine(
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

                    updatedLocalDataSource.saveWeather(oneCallResponse.data, cityItem)

                    localDataSource.insertCityItemToHistorySearch(cityItem)
                    localDataSource.saveWeather(weatherItem)
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
                        localDataSource.saveWeather(weatherItem)
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

                        updatedLocalDataSource.saveWeather(oneCallResponse.data, cityItem)

                        val dailyWeather = oneCallResponse.data.daily?.map {

                            DailyWeather(
                                windDirection = it.windDeg.orZero(),
                                windSpeed = it.windSpeed.orZero(),
                                weatherDescription = it.weather?.first()?.description.orEmpty(),
                                weatherIcon = IconMapper.map(it.weather?.first()?.id),
                                dateTime = it.dt.toTimeStamp(),
                                humidity = it.humidity.orZero(),
                                pressure = it.pressure.orZero(),
                                feelsLike = DataMapper.getFeelsLikeDailyTempItem(it),
                                dailyWeatherItem = DataMapper.getUpdatedDailyTempItem(it),
                                sunset = it.sunset.toTimeStamp(),
                                sunrise = it.sunrise.toTimeStamp()
                            )
                        } ?: listOf()

                        val hourlyWeather = oneCallResponse.data.hourly?.map {
                            HourlyWeather(
                                windDirection = it.windDeg.orZero(),
                                windSpeed = it.windSpeed.orZero(),
                                weatherDescription = it.weather?.first()?.description.orEmpty(),
                                weatherIcon = IconMapper.map(it.weather?.first()?.id),
                                dateTime = it.dt.toTimeStamp(),
                                humidity = it.humidity.orZero(),
                                pressure = it.pressure.orZero(),
                                currentTemp = it.temp.orZero(),
                                feelsLike = it.feelsLike.orZero()
                            )
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