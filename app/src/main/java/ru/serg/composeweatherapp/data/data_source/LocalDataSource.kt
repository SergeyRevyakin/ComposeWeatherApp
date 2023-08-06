package ru.serg.composeweatherapp.data.data_source

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.utils.toCityEntity
import ru.serg.composeweatherapp.utils.toTimeStamp
import ru.serg.composeweatherapp.utils.weather_mapper.IconMapper
import ru.serg.database.room.dao.CityDao
import ru.serg.database.room.dao.WeatherDao
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity
import ru.serg.database.room.entity.toCityItem
import ru.serg.database.toWeatherItem
import ru.serg.model.CityItem
import ru.serg.model.UpdatedWeatherItem
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
    private val cityDao: CityDao
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getWeatherFlow(): Flow<List<UpdatedWeatherItem>> {
        scope.launch {
            weatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }

        return weatherDao.getWeatherWithCity().map { list ->
            list.map {
                it.toWeatherItem()
            }
        }
    }

    fun saveWeather(oneCallResponse: OneCallResponse, cityItem: CityItem) {
        scope.launch {
            val dailyWeather = oneCallResponse.daily?.map {

                DailyWeatherEntity(
                    windDirection = it.windDeg,
                    windSpeed = it.windSpeed,
                    weatherDescription = it.weather?.first()?.description,
                    weatherIcon = IconMapper.map(it.weather?.first()?.id),
                    dateTime = it.dt.toTimeStamp(),
                    humidity = it.humidity,
                    pressure = it.pressure,
                    feelsLike = DataMapper.getFeelsLikeDailyTempItem(it),
                    dailyWeatherItem = DataMapper.getUpdatedDailyTempItem(it),
                    cityId = cityItem.id,
                    sunset = it.sunset.toTimeStamp(),
                    sunrise = it.sunrise.toTimeStamp(),
                    uvi = it.uvi
                )
            } ?: listOf()

            val hourlyWeather = oneCallResponse.hourly?.map {
                HourlyWeatherEntity(
                    windDirection = it.windDeg,
                    windSpeed = it.windSpeed,
                    weatherDescription = it.weather?.first()?.description,
                    weatherIcon = IconMapper.map(it.weather?.first()?.id),
                    dateTime = it.dt.toTimeStamp(),
                    humidity = it.humidity,
                    pressure = it.pressure,
                    cityId = cityItem.id,
                    currentTemp = it.temp,
                    feelsLike = it.feelsLike,
                    uvi = it.uvi
                )
            } ?: listOf()

            weatherDao.saveWeather(hourlyWeather, dailyWeather, cityItem.toCityEntity())
            weatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }
    }


    suspend fun deleteCityItemToHistorySearch(cityItem: CityItem) {
        cityDao.deleteCityFromHistory(cityItem.toCityEntity())
    }

    suspend fun insertCityItemToSearchHistory(cityItem: CityItem) {
        cityDao.addCityToHistory(
            cityItem.toCityEntity()
        )
    }

    fun getFavouriteCity(): Flow<CityItem> {
        return cityDao.citySearchHistoryFlow().map { list ->
            list.map { entity ->
                entity.toCityItem()
            }.find {
                it.isFavorite
            } ?: list.first().toCityItem()
        }.distinctUntilChanged()
    }

    fun getCitySearchHistory() = cityDao.citySearchHistoryFlow().map {
        it.map { cityEntity ->
            cityEntity.toCityItem()
        }
    }.distinctUntilChanged()

}