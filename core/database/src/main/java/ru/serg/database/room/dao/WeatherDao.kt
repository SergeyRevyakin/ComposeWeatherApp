package ru.serg.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.serg.database.Constants
import ru.serg.database.room.CityWeather
import ru.serg.database.room.entity.AlertEntity
import ru.serg.database.room.entity.CityEntity
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity

@Dao
interface WeatherDao {

    @Transaction
    suspend fun saveWeather(
        hourlyWeatherEntities: List<HourlyWeatherEntity>,
        dailyWeatherEntities: List<DailyWeatherEntity>,
        alertEntities: List<AlertEntity>,
        cityEntity: CityEntity
    ) {
        deleteWeather(cityEntity.id)

        insertWeatherItemEntity(dailyWeatherEntities)
        insertHourlyEntity(hourlyWeatherEntities)
        insertAlertEntity(alertEntities)
        saveCity(cityEntity)
    }

    @Transaction
    suspend fun deleteWeather(cityId: Int) {
        deleteWeatherItemEntities(cityId)
        deleteHourlyWeatherEntities(cityId)
        deleteAlertEntities(cityId)
        deleteCity(cityId)
    }

    @Transaction
    suspend fun cleanupOutdatedWeather(timestamp: Long) {
        cleanupOutdatedHourlyWeatherEntities(timestamp)
        cleanupOutdatedWeatherItemEntities(timestamp)
        cleanupOutdatedAlertEntities(timestamp)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCity(cityEntity: CityEntity)

    @Query("DELETE FROM ${Constants.CITY_TABLE} WHERE ${Constants.CITY_ID} =:cityId")
    suspend fun deleteCity(cityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherItemEntity(weatherItemList: List<DailyWeatherEntity>)

    @Query("DELETE FROM ${Constants.DAY_WEATHER_TABLE} WHERE dateTime < :currentTimeStamp")
    suspend fun cleanupOutdatedWeatherItemEntities(currentTimeStamp: Long)

    @Query("DELETE FROM ${Constants.DAY_WEATHER_TABLE} WHERE ${Constants.CITY_ID} =:cityId")
    suspend fun deleteWeatherItemEntities(cityId: Int)

    @Transaction
    @Query("SELECT * FROM ${Constants.CITY_TABLE}")
    fun getWeatherWithCity(): Flow<List<CityWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyEntity(hourlyWeatherEntities: List<HourlyWeatherEntity>)

    @Query("DELETE FROM ${Constants.HOUR_WEATHER_TABLE} WHERE dateTime < :currentTimeStamp")
    suspend fun cleanupOutdatedHourlyWeatherEntities(currentTimeStamp: Long)

    @Query("DELETE FROM ${Constants.HOUR_WEATHER_TABLE} WHERE ${Constants.CITY_ID} = :cityId")
    suspend fun deleteHourlyWeatherEntities(cityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlertEntity(alertEntities: List<AlertEntity>)

    @Query("DELETE FROM ${Constants.ALERT_TABLE} WHERE endAt < :currentTimeStamp")
    suspend fun cleanupOutdatedAlertEntities(currentTimeStamp: Long)

    @Query("DELETE FROM ${Constants.ALERT_TABLE} WHERE ${Constants.CITY_ID} = :cityId")
    suspend fun deleteAlertEntities(cityId: Int)

}