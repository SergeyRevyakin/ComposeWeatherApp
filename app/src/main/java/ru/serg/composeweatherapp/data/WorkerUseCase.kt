@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.mapLatest
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository
) {

    fun fetchFavouriteCity() =
        localDataSource.getFavouriteCity().mapLatest {
            weatherRepository.fetchCityWeather(it)
        }.flattenConcat()
}
