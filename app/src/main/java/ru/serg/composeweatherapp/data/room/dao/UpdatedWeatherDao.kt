package ru.serg.composeweatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import ru.serg.composeweatherapp.data.room.entity.UpdatedCityWeather
import ru.serg.composeweatherapp.data.room.entity.UpdatedDailyWeatherEntity
import ru.serg.composeweatherapp.data.room.entity.UpdatedHourlyWeatherEntity
import ru.serg.composeweatherapp.utils.Constants

@Dao
interface UpdatedWeatherDao {

    @Transaction
    suspend fun saveWeather(
        hourlyWeatherEntities: List<UpdatedHourlyWeatherEntity>,
        dailyWeatherEntities: List<UpdatedDailyWeatherEntity>,
        cityEntity: CityEntity
    ) {
        insertWeatherItemEntity(dailyWeatherEntities)
        insertHourlyEntity(hourlyWeatherEntities)
        saveCity(cityEntity)
    }

    @Transaction
    suspend fun deleteWeather(cityId: Int) {
        deleteWeatherItemEntities(cityId)
        deleteHourlyWeatherEntities(cityId)
    }

    @Transaction
    suspend fun cleanupOutdatedWeather(timestamp: Long) {
        cleanupOutdatedHourlyWeatherEntities(timestamp)
        cleanupOutdatedWeatherItemEntities(timestamp)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCity(cityEntity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherItemEntity(weatherItemList: List<UpdatedDailyWeatherEntity>)

    @Query("DELETE FROM ${Constants.UPDATED_WEATHER_TABLE} WHERE dateTime < :currentTimeStamp")
    suspend fun cleanupOutdatedWeatherItemEntities(currentTimeStamp: Long)

    @Query("DELETE FROM ${Constants.UPDATED_WEATHER_TABLE} WHERE ${Constants.CITY_ID} =:cityId")
    suspend fun deleteWeatherItemEntities(cityId: Int)

    @Transaction
    @Query("SELECT * FROM ${Constants.CITY_TABLE}")
    fun getWeatherWithCity(): Flow<List<UpdatedCityWeather>>

    @Query("SELECT * FROM ${Constants.HOUR_WEATHER_TABLE} WHERE ${Constants.CITY_ID} =:cityId")
    fun getHourlyItemsByCity(cityId: Int): Flow<List<UpdatedHourlyWeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyEntity(hourlyWeatherEntities: List<UpdatedHourlyWeatherEntity>)

    @Query("DELETE FROM ${Constants.HOUR_WEATHER_TABLE} WHERE dateTime < :currentTimeStamp")
    suspend fun cleanupOutdatedHourlyWeatherEntities(currentTimeStamp: Long)

    @Query("DELETE FROM ${Constants.HOUR_WEATHER_TABLE} WHERE ${Constants.CITY_ID} = :cityId")
    suspend fun deleteHourlyWeatherEntities(cityId: Int)

}