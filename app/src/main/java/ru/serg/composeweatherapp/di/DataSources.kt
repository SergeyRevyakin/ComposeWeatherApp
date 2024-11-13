package ru.serg.composeweatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.serg.datastore.DataStoreDataSource
import ru.serg.datastore.DataStoreDataSourceImpl
import ru.serg.local.LocalDataSource
import ru.serg.local.LocalDataSourceImpl
import ru.serg.location.LocationDataSource
import ru.serg.location.LocationService
import ru.serg.network.RemoteDataSource
import ru.serg.network.RemoteDataSourceImpl
import ru.serg.network_weather_api.VisualCrossingRemoteDataSource
import ru.serg.network_weather_api.VisualCrossingRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSources {
    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        localDataRepository: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherApiRemoteDataSource(
        remoteDataSource: VisualCrossingRemoteDataSourceImpl
    ): VisualCrossingRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocationDataSource(
        locationDataSource: LocationDataSource
    ): LocationService

    @Binds
    @Singleton
    abstract fun bindDataStoreDataSource(
        dataStoreDataSourceImpl: DataStoreDataSourceImpl
    ): DataStoreDataSource
}