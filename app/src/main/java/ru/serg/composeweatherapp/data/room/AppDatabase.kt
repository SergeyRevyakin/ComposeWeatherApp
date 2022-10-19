package ru.serg.composeweatherapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.serg.composeweatherapp.data.room.dao.CityHistorySearchDao
import ru.serg.composeweatherapp.data.room.dao.LastLocationDao
import ru.serg.composeweatherapp.data.room.dao.WeatherDao
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import ru.serg.composeweatherapp.data.room.entity.LastLocationEntity

@Database(entities = [WeatherUnit::class, LastLocationEntity::class, CityEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherUnitsDao(): WeatherDao
    abstract fun lastLocationDao(): LastLocationDao
    abstract fun cityHistorySearchDao(): CityHistorySearchDao
}