package ru.serg.composeweatherapp.data.data_source

import com.serg.model.Coordinates
import kotlinx.coroutines.flow.Flow

interface LocationService {
    fun isLocationAvailable(): Boolean

    fun getLocationUpdate(
        isOneTimeRequest: Boolean = true,
        updateFrequency: Long = 15
    ): Flow<Coordinates>

    class LocationException(message: String) : Exception()
}