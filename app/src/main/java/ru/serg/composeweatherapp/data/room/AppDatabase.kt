package ru.serg.composeweatherapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherUnit::class, LastLocationEntity::class, CityEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherUnitsDao(): WeatherDao
    abstract fun lastLocationDao(): LastLocationDao
    abstract fun cityHistorySearchDao(): CityHistorySearchDao
}