package ru.serg.weather

import com.serg.network_self_proxy.SelfProxyRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.serg.local.LocalDataSource
import ru.serg.model.AlertItem
import ru.serg.model.CityItem
import ru.serg.model.Coordinates
import ru.serg.model.WeatherItem
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val selfProxyRemoteDataSource: SelfProxyRemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun fetchCurrentLocationWeather(
        coordinates: Coordinates,
    ): Flow<WeatherItem> =
        selfProxyRemoteDataSource.getSelfProxyForecast(coordinates.latitude, coordinates.longitude)
            .map { resp ->

                val cityItem = SelfDataMapper.mapCityItem(resp, true)

                val hourlyWeather = resp.hourlyList?.map { hourlyWeatherModel ->
                    SelfDataMapper.mapHourlyWeather(
                        hourlyWeatherModel,
                        resp.airPollutionList?.firstOrNull { it.timeStamp == hourlyWeatherModel.dateTime })
                } ?: emptyList()

                val dailyWeather =
                    resp.dailyList?.map { SelfDataMapper.mapDailyWeather(it) } ?: emptyList()

                val alerts = resp.alertList?.map {
                    AlertItem(
                        startAt = it.startTime.orZero(),
                        endAt = it.endTime.orZero(),
                        title = it.title.orEmpty(),
                        description = it.description.orEmpty()
                    )
                } ?: emptyList()

                if (dailyWeather.isNotEmpty() && hourlyWeather.isNotEmpty()) {
                    localDataSource.saveWeather(hourlyWeather, dailyWeather, alerts, cityItem)
                    localDataSource.insertCityItemToSearchHistory(cityItem)
                }

                WeatherItem(
                    cityItem,
                    dailyWeather,
                    hourlyWeather,
                    alerts,
                    alerts.firstOrNull()?.description
                )
            }.flowOn(Dispatchers.IO)


    fun getCityWeatherFlow(
        cityItem: CityItem,
        isResultSavingRequired: Boolean = true
    ) = selfProxyRemoteDataSource.getSelfProxyForecast(cityItem.latitude, cityItem.longitude)
        .map { resp ->

            val hourlyWeather = resp.hourlyList?.map { hourlyWeatherModel ->
                SelfDataMapper.mapHourlyWeather(
                    hourlyWeatherModel,
                    resp.airPollutionList?.firstOrNull { it.timeStamp == hourlyWeatherModel.dateTime })
            } ?: emptyList()

            val dailyWeather =
                resp.dailyList?.map { SelfDataMapper.mapDailyWeather(it) } ?: emptyList()

            val alerts = resp.alertList?.map {
                AlertItem(
                    startAt = it.startTime.orZero(),
                    endAt = it.endTime.orZero(),
                    title = it.title.orEmpty(),
                    description = it.description.orEmpty()
                )
            } ?: emptyList()

            val updatedCityItem = cityItem.copy(
                lastTimeUpdated = System.currentTimeMillis(),
                secondsOffset = resp.city?.secondsOffset ?: 0L
            )



            if (dailyWeather.isNotEmpty() && hourlyWeather.isNotEmpty() && isResultSavingRequired) {
                localDataSource.saveWeather(hourlyWeather, dailyWeather, alerts, updatedCityItem)
            }

            WeatherItem(
                cityItem,
                dailyWeather,
                hourlyWeather,
                alerts,
                alerts.firstOrNull()?.description
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
}