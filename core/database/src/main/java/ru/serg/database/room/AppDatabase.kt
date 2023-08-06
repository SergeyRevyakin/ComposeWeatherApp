package ru.serg.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.serg.database.room.dao.CityDao
import ru.serg.database.room.dao.WeatherDao
import ru.serg.database.room.entity.CityEntity
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity

@Database(
    entities = [CityEntity::class, DailyWeatherEntity::class, HourlyWeatherEntity::class],
    autoMigrations = [
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    abstract fun weatherDao(): WeatherDao
}