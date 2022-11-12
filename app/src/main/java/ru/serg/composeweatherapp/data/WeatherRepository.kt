package ru.serg.composeweatherapp.data

import io.ktor.util.date.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.data.*
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.Ext.isNearTo
import ru.serg.composeweatherapp.utils.IconMapper
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.NetworkStatus
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStoreDataSource: DataStoreDataSource,
    private val networkStatus: NetworkStatus
) {

    suspend fun fetchCurrentLocationWeather(coordinatesWrapper: CoordinatesWrapper): Flow<NetworkResult<WeatherItem>> =
        fetchWeather(coordinatesWrapper.latitude, coordinatesWrapper.longitude)

    suspend fun fetchCityWeather(cityItem: CityItem): Flow<NetworkResult<WeatherItem>> =
        getLocalWeather(cityItem)


    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getLocalWeather(cityItem: CityItem): Flow<NetworkResult<WeatherItem>> =
        localDataSource.getCurrentWeatherItem().flatMapLatest { list ->
            list.findLast {
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
                fetchWeather(cityItem)
            } else {
                flowOf(
                    NetworkResult.Error(
                        message = null,
                        errorTextResource = R.string.no_connection
                    )
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun fetchWeather(
        latitude: Double,
        longitude: Double
    ): Flow<NetworkResult<WeatherItem>> =
        localDataSource.getCurrentWeatherItem().flatMapLatest { list ->

            list.find {
                it.cityItem?.latitude isNearTo latitude &&
                        it.cityItem?.longitude isNearTo longitude
            }?.let { item ->
                dataStoreDataSource.fetchFrequency.flatMapLatest {
                    val delayInHours = Constants.HOUR_FREQUENCY_LIST[it]
                    if (item.lastUpdatedTime > (getTimeMillis() - (delayInHours * 60L * 1000L))) {
                        flowOf(NetworkResult.Success(item))
                    } else {
                        if (networkStatus.isNetworkConnected()) {
                            fetchCoordinatesWeather(latitude, longitude)
                        } else {
                            flowOf(NetworkResult.Success(item))
                        }
                    }
                }
            } ?: if (networkStatus.isNetworkConnected()) {
                fetchCoordinatesWeather(latitude, longitude)
            } else {
                flowOf(
                    NetworkResult.Error(
                        message = null,
                        errorTextResource = R.string.no_connection
                    )
                )
            }
        }

    private suspend fun fetchCoordinatesWeather(latitude: Double, longitude: Double) = combine(
        remoteDataSource.getWeatherW(latitude, longitude),
        remoteDataSource.getWeather(latitude, longitude)
    ) { weatherResponse, oneCallResponse ->
        when {
            (weatherResponse is NetworkResult.Loading || oneCallResponse is NetworkResult.Loading) -> {
                NetworkResult.Loading()
            }
            (weatherResponse is NetworkResult.Error || oneCallResponse is NetworkResult.Error) -> {
                NetworkResult.Error(weatherResponse.data?.message ?: oneCallResponse.data?.message)
            }
            (weatherResponse is NetworkResult.Success && oneCallResponse is NetworkResult.Success) -> {
                val cityItem = CityItem(
                    name = weatherResponse.data?.name.orEmpty(),
                    country = weatherResponse.data?.sys?.country,
                    latitude = weatherResponse.data?.coord?.lat ?: 0.0,
                    longitude = weatherResponse.data?.coord?.lon ?: 0.0,
                    true
                )

                val hourlyList = oneCallResponse.data?.hourly?.map {
                    HourWeatherItem(
                        weatherIcon = IconMapper.map(it?.weather?.first()?.id),
                        currentTemp = it?.feelsLike ?: 0.0,
                        timestamp = (it?.dt ?: 0) * 1000
                    )
                } ?: listOf()

                val dailyList = oneCallResponse.data?.daily?.map { daily ->
                    val feelsLikeIntraDay = IntraDayTempItem(
                        morningTemp = daily?.feelsLike?.morn,
                        dayTemp = daily?.feelsLike?.day,
                        eveningTemp = daily?.feelsLike?.eve,
                        nightTemp = daily?.feelsLike?.night,
                    )

                    val tempIntraDay = IntraDayTempItem(
                        morningTemp = daily?.temp?.morn,
                        dayTemp = daily?.temp?.day,
                        eveningTemp = daily?.temp?.eve,
                        nightTemp = daily?.temp?.night,
                    )

                    DayWeatherItem(
                        feelsLike = feelsLikeIntraDay,
                        temp = tempIntraDay,
                        windSpeed = daily?.windSpeed,
                        windDirection = daily?.windDeg,
                        humidity = daily?.humidity,
                        pressure = daily?.pressure,
                        weatherDescription = daily?.weather?.first()?.description,
                        weatherIcon = IconMapper.map(daily?.weather?.first()?.id),
                        dateTime = (daily?.dt ?: 0) * 1000L,
                        sunrise = (daily?.sunrise?.toLong() ?: 0L) * 1000L,
                        sunset = (daily?.sunset?.toLong() ?: 0L) * 1000L
                    )
                } ?: emptyList()

                val weatherItem = WeatherItem(
                    feelsLike = weatherResponse.data?.main?.feelsLike,
                    currentTemp = weatherResponse.data?.main?.temp,
                    windSpeed = weatherResponse.data?.wind?.speed,
                    windDirection = weatherResponse.data?.wind?.deg,
                    humidity = weatherResponse.data?.main?.humidity,
                    pressure = weatherResponse.data?.main?.pressure,
                    weatherDescription = weatherResponse.data?.weather?.first()?.description,
                    weatherIcon = IconMapper.map(weatherResponse.data?.weather?.first()?.id),
                    dateTime = (weatherResponse.data?.dt?.toLong() ?: 0) * 1000L,
                    cityItem = cityItem,
                    lastUpdatedTime = getTimeMillis(),
                    dailyWeatherList = dailyList,
                    hourlyWeatherList = hourlyList
                )
                localDataSource.saveWeather(weatherItem)
                localDataSource.insertCityItemToHistorySearch(cityItem)
                NetworkResult.Success(weatherItem)

            }

            else -> NetworkResult.Loading()
        }
    }

    suspend fun fetchWeather(
        cityItem: CityItem
    ): Flow<NetworkResult<WeatherItem>> =
        combine(
            remoteDataSource.getWeatherW(cityItem.latitude, cityItem.longitude),
            remoteDataSource.getWeather(cityItem.latitude, cityItem.longitude)
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

                    val hourlyList = oneCallResponse.data?.hourly?.map {
                        HourWeatherItem(
                            weatherIcon = IconMapper.map(it?.weather?.first()?.id),
                            currentTemp = it?.feelsLike ?: 0.0,
                            timestamp = (it?.dt ?: 0) * 1000L
                        )
                    } ?: listOf()

                    val dailyList = oneCallResponse.data?.daily?.map { daily ->
                        val feelsLikeIntraDay = IntraDayTempItem(
                            morningTemp = daily?.feelsLike?.morn,
                            dayTemp = daily?.feelsLike?.day,
                            eveningTemp = daily?.feelsLike?.eve,
                            nightTemp = daily?.feelsLike?.night,
                        )

                        val tempIntraDay = IntraDayTempItem(
                            morningTemp = daily?.temp?.morn,
                            dayTemp = daily?.temp?.day,
                            eveningTemp = daily?.temp?.eve,
                            nightTemp = daily?.temp?.night,
                        )

                        DayWeatherItem(
                            feelsLike = feelsLikeIntraDay,
                            temp = tempIntraDay,
                            windSpeed = daily?.windSpeed,
                            windDirection = daily?.windDeg,
                            humidity = daily?.humidity,
                            pressure = daily?.pressure,
                            weatherDescription = daily?.weather?.first()?.description,
                            weatherIcon = IconMapper.map(daily?.weather?.first()?.id),
                            dateTime = (daily?.dt ?: 0) * 1000L,
                            sunrise = (daily?.sunrise?.toLong() ?: 0L) * 1000L,
                            sunset = (daily?.sunset?.toLong() ?: 0L) * 1000L
                        )
                    } ?: emptyList()

                    val weatherItem = WeatherItem(
                        feelsLike = weatherResponse.data?.main?.feelsLike,
                        currentTemp = weatherResponse.data?.main?.temp,
                        windSpeed = weatherResponse.data?.wind?.speed,
                        windDirection = weatherResponse.data?.wind?.deg,
                        humidity = weatherResponse.data?.main?.humidity,
                        pressure = weatherResponse.data?.main?.pressure,
                        weatherDescription = weatherResponse.data?.weather?.first()?.description,
                        weatherIcon = IconMapper.map(weatherResponse.data?.weather?.first()?.id),
                        dateTime = (weatherResponse.data?.dt?.toLong() ?: 0) * 1000L,
                        cityItem = cityItem,
                        lastUpdatedTime = getTimeMillis(),
                        dailyWeatherList = dailyList,
                        hourlyWeatherList = hourlyList
                    )

                    localDataSource.saveWeather(weatherItem)
                    NetworkResult.Success(weatherItem)

                }

                else -> NetworkResult.Loading()
            }
        }

}