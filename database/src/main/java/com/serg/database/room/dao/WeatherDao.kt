package com.serg.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.serg.database.Constants
import com.serg.database.room.entity.WeatherItemEntity
import com.serg.database.room.entity.WeatherWithCity
import kotlinx.coroutines.flow.Flow

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