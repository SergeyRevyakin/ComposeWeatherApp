package ru.serg.network_weather_api

import kotlinx.coroutines.flow.Flow
import ru.serg.network_weather_api.dto.VisualCrossingResponse

fun interface VisualCrossingRemoteDataSource {
    fun getVisualCrossingForecast(lat: Double, lon: Double): Flow<VisualCrossingResponse>
}