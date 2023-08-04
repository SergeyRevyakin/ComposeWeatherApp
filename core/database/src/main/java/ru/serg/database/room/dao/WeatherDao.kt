package ru.serg.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.serg.database.Constants
import ru.serg.database.room.entity.WeatherWithCity

@Dao
interface WeatherDao {

    @Transaction
    @Query("SELECT * FROM ${Constants.WEATHER_ITEMS}")
    fun getWeatherWithCity(): Flow<List<WeatherWithCity>>

    @Transaction
    @Query("")
    suspend fun deleteWeatherWithCity(cityName: String) {
        deleteFromWeather(cityName)
        deleteFromCity(cityName)
    }

    @Query("DELETE FROM ${Constants.WEATHER_ITEMS} WHERE cityName=:cityName")
    suspend fun deleteFromWeather(cityName: String)

    @Query("DELETE FROM ${Constants.CITY_TABLE} WHERE cityName=:cityName")
    suspend fun deleteFromCity(cityName: String)
}