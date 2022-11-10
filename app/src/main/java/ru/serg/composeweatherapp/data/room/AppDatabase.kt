package ru.serg.composeweatherapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.serg.composeweatherapp.data.room.dao.CityHistorySearchDao
import ru.serg.composeweatherapp.data.room.dao.LastLocationDao
import ru.serg.composeweatherapp.data.room.dao.WeatherDao
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import ru.serg.composeweatherapp.data.room.entity.LastLocationEntity
import ru.serg.composeweatherapp.data.room.entity.WeatherItemEntity

@TypeConverters(value = [WeatherTypeConverters::class])
@Database(
    entities = [WeatherUnit::class, LastLocationEntity::class, CityEntity::class, WeatherItemEntity::class],
    version = 11
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherUnitsDao(): WeatherDao
    abstract fun lastLocationDao(): LastLocationDao
    abstract fun cityHistorySearchDao(): CityHistorySearchDao
}