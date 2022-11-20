package ru.serg.composeweatherapp.data.data_source

import io.ktor.util.date.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

class LocalDataSource @Inject constructor(
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

    fun getCityHistorySearchDao(): Flow<List<CityItem>> {
        return cityHistorySearchDao.getCitySearchHistory().map { list ->
            list.map { entity ->
                entity.toCityItem()
            }.filter {
                !it.isFavorite
            }
        }
    }

    fun getFavouriteCity(): Flow<CityItem> {
        return cityHistorySearchDao.getCitySearchHistory().map { list ->
            list.map { entity ->
                entity.toCityItem()
            }.first {
                it.isFavorite
            }
        }
    }

    suspend fun insertCityItemToHistorySearch(cityItem: CityItem) {
        cityHistorySearchDao.addCityToHistory(
            cityItem.toCityEntity()
        )
    }

    suspend fun deleteCityItemToHistorySearch(cityItem: CityItem) {
        weatherDao.deleteWeatherWithCity(cityItem.name)
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
                            it.cityEntity?.cityName.orEmpty(),
                            it.cityEntity?.country,
                            it.cityEntity?.latitude ?: 0.0,
                            it.cityEntity?.longitude ?: 0.0,
                            it.cityEntity?.isFavorite ?: false
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