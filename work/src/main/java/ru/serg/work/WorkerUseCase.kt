package ru.serg.work

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import ru.serg.local.LocalDataSource
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke() =
        localDataSource.getFavouriteCity().first().run {
            weatherRepository.getCityWeatherFlow(this)
        }.flowOn(Dispatchers.IO)

}
