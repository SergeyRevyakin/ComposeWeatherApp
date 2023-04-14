package ru.serg.composeweatherapp.data.data_source

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.data.dto.WeatherItem
import ru.serg.composeweatherapp.data.room.dao.CityHistorySearchDao
import ru.serg.composeweatherapp.data.room.dao.WeatherDao
import ru.serg.composeweatherapp.utils.toCityEntity
import ru.serg.composeweatherapp.utils.toCityItem
import ru.serg.composeweatherapp.utils.toWeatherEntity
import ru.serg.composeweatherapp.utils.toWeatherItem
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
    private val cityHistorySearchDao: CityHistorySearchDao
) {

    fun getCityHistorySearch(): Flow<List<CityItem>> {
        return cityHistorySearchDao.citySearchHistoryFlow().map { list ->
            list.map { entity ->
                entity.toCityItem()
            }.filter {
                !it.isFavorite
            }
        }
    }

    fun getFavouriteCity(): Flow<CityItem> {
        return cityHistorySearchDao.citySearchHistoryFlow().map { list ->
            list.map { entity ->
                entity.toCityItem()
            }.find {
                it.isFavorite
            } ?: list.first().toCityItem()
        }
    }

    suspend fun insertCityItemToHistorySearch(cityItem: CityItem) {
        Log.e(this::class.simpleName, "Entering insert fun\n $cityItem")
        if (cityItem.isFavorite) {
            Log.e(this::class.simpleName, "Entering if")
            cityHistorySearchDao.citySearchHistory().forEach {
                if (it.isFavorite && it.cityName != cityItem.name) {
                    Log.e(this::class.simpleName, "Deleting $it")
                    deleteCityItemToHistorySearch(it.toCityItem())
                }
            }
        }
        Log.e(this::class.simpleName, "Continue")
        cityHistorySearchDao.addCityToHistory(
            cityItem.toCityEntity()
        )
    }

    suspend fun deleteCityItemToHistorySearch(cityItem: CityItem) {
        weatherDao.deleteWeatherWithCity(cityItem.name)
    }

    fun getCurrentWeatherItem(): Flow<List<WeatherItem>> =
        weatherDao.getWeatherWithCity().map { list ->
            list.map {
                it.toWeatherItem()
            }
        }

    suspend fun saveWeather(weatherItem: WeatherItem) {
        weatherDao.saveWeatherEntity(
            weatherItem.toWeatherEntity()
        )
    }
}