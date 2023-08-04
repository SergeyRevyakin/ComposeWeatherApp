package com.serg.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.serg.database.room.dao.CityHistorySearchDao
import com.serg.database.room.dao.UpdatedWeatherDao
import com.serg.database.room.dao.WeatherDao
import com.serg.database.room.entity.CityEntity
import com.serg.database.room.entity.UpdatedDailyWeatherEntity
import com.serg.database.room.entity.UpdatedHourlyWeatherEntity
import com.serg.database.room.entity.WeatherItemEntity

@TypeConverters(value = [WeatherTypeConverters::class])
@Database(
    entities = [CityEntity::class, WeatherItemEntity::class, UpdatedDailyWeatherEntity::class, UpdatedHourlyWeatherEntity::class],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    abstract fun cityHistorySearchDao(): CityHistorySearchDao

    abstract fun updatedWeatherDao(): UpdatedWeatherDao
}