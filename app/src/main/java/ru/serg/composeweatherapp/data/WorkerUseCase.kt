@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.mapLatest
import ru.serg.local.LocalRepository
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val weatherRepository: WeatherRepository
) {

    fun fetchFavouriteCity() =
        localRepository.getFavouriteCity().mapLatest {
            weatherRepository.fetchCityWeather(it)
        }.flattenConcat()
}
