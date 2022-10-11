package ru.serg.composeweatherapp.data

import ru.serg.composeweatherapp.data.room.WeatherDao
import ru.serg.composeweatherapp.data.room.WeatherUnit
import javax.inject.Inject

class LocalRepository @Inject constructor(private val weatherDao: WeatherDao) {

    suspend fun saveInDatabase(weatherUnit: WeatherUnit){
        weatherDao.insertWeatherUnit(weatherUnit)
    }

    suspend fun getQuantity():Int{
        return weatherDao.getAllWeatherUnits().size
    }
}