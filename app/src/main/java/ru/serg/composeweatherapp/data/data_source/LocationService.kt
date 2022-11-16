package ru.serg.composeweatherapp.data.data_source

import kotlinx.coroutines.flow.Flow
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper

interface LocationService {
    fun getLocationUpdate(): Flow<CoordinatesWrapper>

    class LocationException(message: String) : Exception()
}