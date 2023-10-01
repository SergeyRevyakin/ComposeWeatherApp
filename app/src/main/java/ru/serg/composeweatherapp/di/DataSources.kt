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

@Module
@InstallIn(SingletonComponent::class)
interface DataSources {
    @Binds
    fun bindLocalDataSource(
        localDataRepository: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    fun bindRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    fun bindLocationDataSource(
        locationDataSource: LocationDataSource
    ): LocationService

    @Binds
    fun bindDataStoreDataSource(
        dataStoreDataSourceImpl: DataStoreDataSourceImpl
    ): DataStoreDataSource
}