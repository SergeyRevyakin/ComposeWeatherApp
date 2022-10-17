package ru.serg.composeweatherapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import ru.serg.composeweatherapp.data.room.WeatherUnit
import ru.serg.composeweatherapp.utils.NetworkResult
import java.time.LocalDateTime
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    suspend fun fetchWeather(): Flow<Double> {
        return channelFlow {
            localRepository.getLastSavedLocation().let { coordinatesWrapper ->
                remoteRepository.getWeatherW(
                    coordinatesWrapper.latitude,
                    coordinatesWrapper.longitude
                ).collectLatest {
                    if (it is NetworkResult.Success) {
                        send(it.data?.main?.feelsLike ?: 0.0)
                    }
                    localRepository.saveInDatabase(WeatherUnit(name = "USE_CASE_EXECUTED ${it.javaClass.simpleName} ${LocalDateTime.now()}"))
                }
            }
        }
    }
}