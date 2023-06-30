package ru.serg.composeweatherapp.data.data_source

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.data.dto.WeatherItem
import ru.serg.composeweatherapp.data.room.dao.CityHistorySearchDao
import ru.serg.composeweatherapp.data.room.dao.WeatherDao
import ru.serg.composeweatherapp.utils.toCityEntity
import ru.serg.composeweatherapp.utils.toCityItem
import ru.serg.composeweatherapp.utils.toWeatherEntity
import ru.serg.composeweatherapp.utils.toWeatherItem
import javax.inject.Inject

@ExperimentalCoroutinesApi
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
        }.distinctUntilChanged()
    }

    fun getFavouriteCityWithWeather(): Flow<WeatherItem?> {
        return weatherDao.getWeatherWithCity().mapLatest { list ->
            list.firstOrNull {
                it.cityEntity?.isFavorite == true
            }?.toWeatherItem() ?: list.firstOrNull()?.toWeatherItem()
        }.distinctUntilChanged()
    }

    suspend fun insertCityItemToHistorySearch(cityItem: CityItem) {
        Log.e(this::class.simpleName, "Entering insert fun\n $cityItem")
        cityHistorySearchDao.addCityToHistory(
            cityItem.toCityEntity()
        )
    }

    suspend fun deleteCityItemToHistorySearch(cityItem: CityItem) {
        weatherDao.deleteWeatherWithCity(cityItem.name)
    }

    fun getCurrentWeatherItem(): Flow<List<WeatherItem>> =
        weatherDao.getWeatherWithCity().mapLatest { list ->
            list.filter { it.cityEntity?.isFavorite != true }.map {
                it.toWeatherItem()
            }
        }.distinctUntilChanged()

    suspend fun saveWeather(weatherItem: WeatherItem) {
        weatherDao.saveWeatherEntity(
            weatherItem.toWeatherEntity()
        )
    }
}