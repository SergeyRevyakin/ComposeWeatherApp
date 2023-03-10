@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import io.ktor.util.date.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import ru.serg.composeweatherapp.data.data.*
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository
) {

    fun fetchFavouriteCity() =
        localDataSource.getFavouriteCity().flatMapLatest {
            weatherRepository.fetchCityWeather(it, true)
        }
}
