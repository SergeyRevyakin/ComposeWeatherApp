package ru.serg.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.serg.local.LocalDataRepository
import ru.serg.local.LocalRepository

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule {
    @Binds
    fun provideLocalRepository(
        localDataRepository: LocalDataRepository
    ): LocalRepository
}