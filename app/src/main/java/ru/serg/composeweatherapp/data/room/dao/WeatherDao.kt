package ru.serg.composeweatherapp.data.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.serg.composeweatherapp.data.room.WeatherUnit
import ru.serg.composeweatherapp.data.room.entity.WeatherItemEntity
import ru.serg.composeweatherapp.data.room.entity.WeatherWithCity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherUnit(weatherUnit: WeatherUnit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherEntity(weatherItemEntity: WeatherItemEntity)

    @Transaction
    @Query("SELECT * FROM WEATHERITEMENTITY")
    fun getWeatherWithCity(): Flow<List<WeatherWithCity>>
}