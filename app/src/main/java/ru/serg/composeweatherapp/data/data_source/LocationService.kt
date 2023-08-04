package ru.serg.composeweatherapp.data.data_source

import kotlinx.coroutines.flow.Flow
import ru.serg.model.Coordinates

interface LocationService {
    fun isLocationAvailable(): Boolean

    fun getLocationUpdate(
        isOneTimeRequest: Boolean = true,
        updateFrequency: Long = 15
    ): Flow<Coordinates>

    class LocationException(message: String) : Exception()
}