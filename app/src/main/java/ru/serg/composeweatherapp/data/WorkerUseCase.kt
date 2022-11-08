package ru.serg.composeweatherapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.data.room.WeatherUnit
import ru.serg.composeweatherapp.utils.NetworkResult
import java.time.LocalDateTime
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun fetchWeather(): Flow<Double> {
        return channelFlow {
            localDataSource.getLastSavedLocation().let { coordinatesWrapper ->
                remoteDataSource.getWeatherW(
                    coordinatesWrapper.latitude,
                    coordinatesWrapper.longitude
                ).collectLatest {
                    if (it is NetworkResult.Success) {
                        send(it.data?.main?.feelsLike ?: 0.0)
                    }
                    localDataSource.saveInDatabase(WeatherUnit(name = "USE_CASE_EXECUTED ${it.javaClass.simpleName} ${LocalDateTime.now()}"))
                }
            }
        }
    }
}