package ru.serg.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.serg.database.room.dao.CityDao
import ru.serg.database.room.dao.WeatherDao
import ru.serg.database.room.entity.CityEntity
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity

@Database(
    entities = [CityEntity::class, DailyWeatherEntity::class, HourlyWeatherEntity::class],
    autoMigrations = [],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    abstract fun weatherDao(): WeatherDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE day_weather_table ADD COLUMN precipitationProbability INTEGER DEFAULT 0 not null")
                db.execSQL("ALTER TABLE hour_weather_table ADD COLUMN precipitationProbability INTEGER DEFAULT 0 not null")
            }
        }
    }
}