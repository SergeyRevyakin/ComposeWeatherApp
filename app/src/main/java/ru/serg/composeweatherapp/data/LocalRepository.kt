package ru.serg.composeweatherapp.data

import io.ktor.util.date.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.data.room.WeatherUnit
import ru.serg.composeweatherapp.data.room.dao.CityHistorySearchDao
import ru.serg.composeweatherapp.data.room.dao.LastLocationDao
import ru.serg.composeweatherapp.data.room.dao.WeatherDao
import ru.serg.composeweatherapp.data.room.entity.WeatherItemEntity
import ru.serg.composeweatherapp.utils.Ext.toCityEntity
import ru.serg.composeweatherapp.utils.Ext.toCityItem
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val lastLocationDao: LastLocationDao,
    private val cityHistorySearchDao: CityHistorySearchDao
) {

    suspend fun saveInDatabase(weatherUnit: WeatherUnit) {
        weatherDao.insertWeatherUnit(weatherUnit)
    }

    suspend fun getLastSavedLocation(): CoordinatesWrapper {
        lastLocationDao.getLocation()?.let {
            return CoordinatesWrapper(it.latitude, it.longitude)
        }
        return CoordinatesWrapper(0.0, 0.0)
    }

    suspend fun getCityHistorySearchDao(): Flow<List<CityItem>> {
        return flow {
            cityHistorySearchDao.getCitySearchHistory().collect { list ->
                emit(list.map { entity ->
                    entity.toCityItem()
                }.filter {
                    !it.isFavorite
                })
            }
        }
    }

    suspend fun insertCityItemToHistorySearch(cityItem: CityItem) {
        cityHistorySearchDao.addCityToHistory(
            cityItem.toCityEntity()
        )
    }

    suspend fun deleteCityItemToHistorySearch(cityItem: CityItem) {
        cityHistorySearchDao.deleteCityFromHistory(
            cityItem.toCityEntity()
        )
    }

    suspend fun getCurrentWeatherItem(): Flow<List<WeatherItem>> {
        return flow {
            weatherDao.getWeatherWithCity().collect { list ->
                emit(list.map {
                    WeatherItem(
                        feelsLike = it.weatherItemEntity.feelsLike,
                        currentTemp = it.weatherItemEntity.currentTemp,
                        windDirection = it.weatherItemEntity.windDirection,
                        windSpeed = it.weatherItemEntity.windSpeed,
                        humidity = it.weatherItemEntity.humidity,
                        pressure = it.weatherItemEntity.pressure,
                        weatherDescription = it.weatherItemEntity.weatherDescription,
                        weatherIcon = it.weatherItemEntity.weatherIcon,
                        dateTime = it.weatherItemEntity.dateTime,
                        cityItem = CityItem(
                            it.cityEntity.cityName,
                            it.cityEntity.country,
                            it.cityEntity.latitude ?: 0.0,
                            it.cityEntity.longitude ?: 0.0,
                            false
                        ),
                        lastUpdatedTime = it.weatherItemEntity.lastUpdatedTime,
                        hourlyWeatherList = it.weatherItemEntity.hourlyWeatherList.list.filter { hourWeatherItem ->
                            hourWeatherItem.timestamp > getTimeMillis()
                        },
                        dailyWeatherList = it.weatherItemEntity.dailyWeatherList.list.filter { dayWeatherItem ->
                            dayWeatherItem.dateTime > getTimeMillis()
                        }
                    )
                })
            }
        }
    }

    suspend fun saveWeather(weatherItem: WeatherItem) {
        weatherDao.saveWeatherEntity(
            WeatherItemEntity(
                feelsLike = weatherItem.feelsLike,
                currentTemp = weatherItem.currentTemp,
                windDirection = weatherItem.windDirection,
                windSpeed = weatherItem.windSpeed,
                humidity = weatherItem.humidity,
                pressure = weatherItem.pressure,
                weatherDescription = weatherItem.weatherDescription,
                weatherIcon = weatherItem.weatherIcon,
                dateTime = weatherItem.dateTime,
                cityName = weatherItem.cityItem?.name.orEmpty(),
                lastUpdatedTime = weatherItem.lastUpdatedTime,
                hourlyWeatherList = WeatherItemEntity.HourItemList(weatherItem.hourlyWeatherList),
                dailyWeatherList = WeatherItemEntity.DailyItemList(weatherItem.dailyWeatherList)
            )
        )
    }
}