package ru.serg.composeweatherapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherUnit(weatherUnit: WeatherUnit)

    @Query("SELECT * FROM WEATHERUNIT")
    suspend fun getAllWeatherUnits(): List<WeatherUnit>
}