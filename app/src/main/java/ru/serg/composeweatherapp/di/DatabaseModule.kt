package ru.serg.composeweatherapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.serg.composeweatherapp.data.room.AppDatabase
import ru.serg.composeweatherapp.data.room.dao.CityHistorySearchDao
import ru.serg.composeweatherapp.data.room.dao.LastLocationDao
import ru.serg.composeweatherapp.data.room.dao.WeatherDao
import ru.serg.composeweatherapp.utils.Constants
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.WEATHER_DATABASE
        )
            .fallbackToDestructiveMigration()//TODO Migration rules
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherUnitsDao()
    }

    @Provides
    @Singleton
    fun provideLastLocationDao(appDatabase: AppDatabase): LastLocationDao {
        return appDatabase.lastLocationDao()
    }

    @Provides
    @Singleton
    fun provideCityHistorySearchDao(appDatabase: AppDatabase): CityHistorySearchDao {
        return appDatabase.cityHistorySearchDao()
    }
}