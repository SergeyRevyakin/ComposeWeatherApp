package ru.serg.composeweatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.data.data_source.LocationDataSource
import ru.serg.composeweatherapp.utils.NetworkStatus
import ru.serg.composeweatherapp.utils.WeatherAlarmManager
import ru.serg.composeweatherapp.utils.WorkerManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStoreDataSource = DataStoreDataSource(context)

    @Singleton
    @Provides
    fun provideNetworkStatus(
        @ApplicationContext context: Context
    ): NetworkStatus = NetworkStatus(context)

    @Singleton
    @Provides
    fun provideLocationService(
        @ApplicationContext context: Context,
    ): LocationDataSource = LocationDataSource(context)

    @Singleton
    @Provides
    fun provideWeatherAlarmManager(
        @ApplicationContext context: Context
    ): WeatherAlarmManager = WeatherAlarmManager(context)

    @Singleton
    @Provides
    fun provideWorkerManager(
        @ApplicationContext context: Context,
    ): WorkerManager = WorkerManager(context)

}