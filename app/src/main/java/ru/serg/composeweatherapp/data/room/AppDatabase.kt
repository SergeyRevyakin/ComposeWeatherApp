package ru.serg.composeweatherapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherUnit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherUnitsDao(): WeatherDao
}