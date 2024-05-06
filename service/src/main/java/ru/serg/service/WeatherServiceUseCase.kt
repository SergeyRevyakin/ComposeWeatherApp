@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.service

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.datastore.DataStoreDataSource
import ru.serg.location.LocationDataSource
import ru.serg.model.WeatherItem
import ru.serg.weather.WeatherRepository
import javax.inject.Inject

class WeatherServiceUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationDataSource,
    private val dataStoreDataSource: DataStoreDataSource
) {
    fun checkCurrentLocationAndWeather(): Flow<ServiceFetchingResult<WeatherItem>> =

        dataStoreDataSource.fetchFrequency.flatMapLatest { fetchFrequency ->
            Log.e(this::class.simpleName, "Fetch frequency $fetchFrequency")
            locationService.getLocationUpdate(
                isOneTimeRequest = true,
                updateFrequency = fetchFrequency.toLong()
            ).flatMapLatest { coordinatesWrapper ->
                Log.e(this::class.simpleName, "Coordinates are $coordinatesWrapper")
                weatherRepository.fetchCurrentLocationWeather(
                    coordinatesWrapper
                ).asResult().map { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Success -> ServiceFetchingResult.Success(
                            networkResult.data
                        )

                        is NetworkResult.Error -> ServiceFetchingResult.Error(
                            networkResult.message.orEmpty()
                        )

                        is NetworkResult.Loading -> ServiceFetchingResult.Loading("Fetching result from server")
                    }
                }
            }
        }
}