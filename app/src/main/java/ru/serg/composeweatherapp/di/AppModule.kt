package ru.serg.composeweatherapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.data.data_source.LocationServiceImpl
import ru.serg.composeweatherapp.utils.NetworkStatus
import ru.serg.composeweatherapp.utils.WeatherAlarmManager
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
    fun provideFusedLocationProviderClient(
        @ApplicationContext app: Context
    ): FusedLocationProviderClient = FusedLocationProviderClient(app)

    @Singleton
    @Provides
    fun provideNetworkStatus(
        @ApplicationContext context: Context
    ): NetworkStatus = NetworkStatus(context)

    @Singleton
    @Provides
    fun provideLocationService(
        @ApplicationContext context: Context,
        client: FusedLocationProviderClient
    ): LocationServiceImpl = LocationServiceImpl(context, client)

    @Singleton
    @Provides
    fun provideWeatherAlarmManager(
        @ApplicationContext context: Context
    ): WeatherAlarmManager = WeatherAlarmManager(context)

}