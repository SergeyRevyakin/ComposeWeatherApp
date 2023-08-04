package com.serg.database

import android.content.Context
import androidx.room.Room
import com.serg.database.room.AppDatabase
import com.serg.database.room.dao.CityHistorySearchDao
import com.serg.database.room.dao.UpdatedWeatherDao
import com.serg.database.room.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun provideCityHistorySearchDao(appDatabase: AppDatabase): CityHistorySearchDao {
        return appDatabase.cityHistorySearchDao()
    }

    @Provides
    @Singleton
    fun provideUpdatedWeatherDao(appDatabase: AppDatabase): UpdatedWeatherDao {
        return appDatabase.updatedWeatherDao()
    }

}