package ru.serg.composeweatherapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.data.room.*
import ru.serg.composeweatherapp.data.room.dao.CityHistorySearchDao
import ru.serg.composeweatherapp.data.room.dao.LastLocationDao
import ru.serg.composeweatherapp.data.room.dao.WeatherDao
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import ru.serg.composeweatherapp.data.room.entity.LastLocationEntity
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val lastLocationDao: LastLocationDao,
    private val cityHistorySearchDao: CityHistorySearchDao
) {

    suspend fun saveInDatabase(weatherUnit: WeatherUnit) {
        weatherDao.insertWeatherUnit(weatherUnit)
    }

    suspend fun getQuantity(): Int {
        return weatherDao.getAllWeatherUnits().size
    }

    suspend fun getLastSavedLocation(): CoordinatesWrapper {
        lastLocationDao.getLocation()?.let {
            return CoordinatesWrapper(it.latitude, it.longitude)
        }
        return CoordinatesWrapper(0.0, 0.0)
    }

    suspend fun saveCurrentLocation(lat: Double, long: Double) {
        lastLocationDao.saveLocation(LastLocationEntity(lat, long))
    }

    suspend fun getCityHistorySearchDao(): Flow<List<CityItem>> {
        return flow {
            cityHistorySearchDao.getCitySearchHistory().collect {
                emit(it.map { entity ->
                    CityItem(
                        entity.name,
                        entity.country,
                        entity.latitude,
                        entity.longitude
                    )
                }
                )
            }
        }
    }

    suspend fun insertCityItemToHistorySearch(cityItem: CityItem) {
        cityHistorySearchDao.addCityToHistory(
            CityEntity(
                cityItem.name,
                cityItem.country,
                cityItem.latitude,
                cityItem.longitude
            )
        )
    }

    suspend fun deleteCityItemToHistorySearch(cityItem: CityItem) {
        cityHistorySearchDao.deleteCityFromHistory(
            CityEntity(
                cityItem.name,
                cityItem.country,
                cityItem.latitude,
                cityItem.longitude
            )
        )
    }
}