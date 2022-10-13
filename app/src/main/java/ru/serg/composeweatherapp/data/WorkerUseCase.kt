package ru.serg.composeweatherapp.data

import kotlinx.coroutines.flow.collectLatest
import ru.serg.composeweatherapp.data.room.WeatherUnit
import java.time.LocalDateTime
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    suspend fun fetchWeather(lat:Double, long: Double){
        remoteRepository.getWeatherW(lat, long).collectLatest {
            localRepository.saveInDatabase(WeatherUnit(name = "USE_CASE_EXECUTED ${it.javaClass.simpleName} ${LocalDateTime.now()}"))
        }
    }
}