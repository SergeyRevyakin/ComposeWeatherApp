@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.work

import com.serg.weather.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.mapLatest
import ru.serg.local.LocalDataSource
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
