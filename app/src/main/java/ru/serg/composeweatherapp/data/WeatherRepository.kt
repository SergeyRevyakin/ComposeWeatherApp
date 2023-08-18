@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import ru.serg.common.NetworkResult
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.utils.orEmpty
import ru.serg.composeweatherapp.utils.orZero
import ru.serg.composeweatherapp.utils.toTimeStamp
import ru.serg.composeweatherapp.utils.weather_mapper.IconMapper
import ru.serg.local.LocalDataSource
import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.UpdatedWeatherItem
import ru.serg.model.WeatherItem
import ru.serg.network.RemoteDataSource
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
                NetworkResult.Loading
            }

            (weatherResponse is NetworkResult.Error || oneCallResponse is NetworkResult.Error) -> {
                NetworkResult.Error(
                    "Error"
                )
            }

            (weatherResponse is NetworkResult.Success && oneCallResponse is NetworkResult.Success) -> {

                val cityItem = DataMapper.mapCityItem(weatherResponse.data, true)

                val weatherItem = DataMapper.getWeatherItem(
                    weatherResponse.data,
                    oneCallResponse.data,
                    cityItem
                )

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
                        sunrise = it.sunrise.toTimeStamp(),
                        uvi = it.uvi.orZero()
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
                        feelsLike = it.feelsLike.orZero(),
                        uvi = it.uvi.orZero()
                    )
                } ?: listOf()

                localDataSource.saveWeather(hourlyWeather, dailyWeather, cityItem)

                localDataSource.insertCityItemToSearchHistory(cityItem)
                NetworkResult.Success(weatherItem)

            }

            else -> NetworkResult.Loading
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
                    NetworkResult.Loading
                }

                (weatherResponse is NetworkResult.Error || oneCallResponse is NetworkResult.Error) -> {
                    NetworkResult.Error(
                        "Error"
                    )
                }

                (weatherResponse is NetworkResult.Success && oneCallResponse is NetworkResult.Success) -> {

                    val weatherItem = DataMapper.getWeatherItem(
                        weatherResponse.data,
                        oneCallResponse.data,
                        cityItem
                    )

                    val dailyWeather = oneCallResponse.data.daily?.map {
                        DataMapper.mapDailyWeather(it)
                    } ?: listOf()

                    val hourlyWeather = oneCallResponse.data.hourly?.map {
                        DataMapper.mapHourlyWeather(it)
                    } ?: listOf()


                    localDataSource.saveWeather(hourlyWeather, dailyWeather, cityItem)
                    NetworkResult.Success(weatherItem)

                }

                else -> NetworkResult.Loading
            }
        }

    fun getCityWeatherFlow(
        cityItem: CityItem
    ) = remoteDataSource.getOneCallWeather(cityItem.latitude, cityItem.longitude)
        .mapLatest { oneCallResponse ->
            when {
                (oneCallResponse is NetworkResult.Loading) -> {
                    NetworkResult.Loading
                }

                (oneCallResponse is NetworkResult.Error) -> {
                    NetworkResult.Error(
                        oneCallResponse.message ?: "Unknown error"
                    )
                }

                (oneCallResponse is NetworkResult.Success) -> {

                    val dailyWeather = oneCallResponse.data.daily?.map {
                        DataMapper.mapDailyWeather(it)
                    } ?: listOf()

                    val hourlyWeather = oneCallResponse.data.hourly?.map {
                        DataMapper.mapHourlyWeather(it)
                    } ?: listOf()


                    localDataSource.saveWeather(hourlyWeather, dailyWeather, cityItem)

                    NetworkResult.Success(Any())

                }

                else -> NetworkResult.Loading
            }
        }

    fun getCityWeatherNoSavingFlow(
        cityItem: CityItem
    ) = remoteDataSource.getOneCallWeather(cityItem.latitude, cityItem.longitude)
        .mapLatest { oneCallResponse ->
            when {
                (oneCallResponse is NetworkResult.Loading) -> {
                    NetworkResult.Loading
                }

                (oneCallResponse is NetworkResult.Error) -> {
                    NetworkResult.Error(
                        oneCallResponse.message ?: "Unknown error"
                    )
                }

                (oneCallResponse is NetworkResult.Success) -> {

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
                }

                else -> NetworkResult.Loading
            }
        }
}