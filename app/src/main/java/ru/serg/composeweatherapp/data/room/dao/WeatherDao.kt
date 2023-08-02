package ru.serg.composeweatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.serg.composeweatherapp.data.room.entity.WeatherItemEntity
import ru.serg.composeweatherapp.data.room.entity.WeatherWithCity
import ru.serg.composeweatherapp.utils.Constants

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherEntity(weatherItemEntity: WeatherItemEntity)

    @Delete
    suspend fun deleteWeatherEntity(weatherItemEntity: WeatherItemEntity)

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