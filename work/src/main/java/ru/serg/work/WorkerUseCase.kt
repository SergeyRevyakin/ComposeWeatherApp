@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.work

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.mapLatest
import ru.serg.datastore.DataStoreDataSource
import ru.serg.local.LocalDataSource
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository,
    private val dataStoreDataSource: DataStoreDataSource
) {

    fun fetchFavouriteCity() =
        localDataSource.getFavouriteCity().mapLatest {
            weatherRepository.getCityWeatherFlow(it)
        }.flattenConcat()

    fun isUserNotificationsOn() =
        dataStoreDataSource.isUserNotificationOn
}
