package ru.serg.composeweatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.serg.common.NetworkStatus
import ru.serg.datastore.dataStore
import ru.serg.location.LocationDataSource
import ru.serg.work.WorkerManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

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
    fun provideWorkerManager(
        @ApplicationContext context: Context,
    ): WorkerManager = WorkerManager(context)

    @Singleton
    @Provides
    fun provideDataSource(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore

    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context = context
}