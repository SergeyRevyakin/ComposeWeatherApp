package ru.serg.composeweatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.serg.composeweatherapp.data.room.WeatherUnit

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherUnit(weatherUnit: WeatherUnit)

    @Query("SELECT * FROM WEATHERUNIT")
    suspend fun getAllWeatherUnits(): List<WeatherUnit>
}