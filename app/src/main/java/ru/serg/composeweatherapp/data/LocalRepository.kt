package ru.serg.composeweatherapp.data

import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.data.room.LastLocationDao
import ru.serg.composeweatherapp.data.room.LastLocationEntity
import ru.serg.composeweatherapp.data.room.WeatherDao
import ru.serg.composeweatherapp.data.room.WeatherUnit
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val lastLocationDao: LastLocationDao
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
}