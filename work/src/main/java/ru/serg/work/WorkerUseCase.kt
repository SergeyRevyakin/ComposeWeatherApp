@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.work

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.serg.local.LocalDataSource
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository,
) {
    operator fun invoke() =
        localDataSource.getFavouriteCity().distinctUntilChanged().map {
            weatherRepository.getCityWeatherFlow(it)
        }.flattenConcat()
            .flowOn(Dispatchers.IO)

}
