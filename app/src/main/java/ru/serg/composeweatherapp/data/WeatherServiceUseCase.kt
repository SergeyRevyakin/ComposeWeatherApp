@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.serg.composeweatherapp.data.dto.WeatherItem
import ru.serg.composeweatherapp.data.data_source.DataStoreRepository
import ru.serg.composeweatherapp.data.data_source.LocationDataSource
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ServiceFetchingResult
import javax.inject.Inject

class WeatherServiceUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationDataSource,
    private val dataStoreRepository: DataStoreRepository
) {
    fun checkCurrentLocationAndWeather(): Flow<ServiceFetchingResult<WeatherItem>> =

        dataStoreRepository.fetchFrequency.flatMapLatest { fetchFrequency ->
            Log.e(this::class.simpleName, "Fetch frequency $fetchFrequency")
            locationService.getLocationUpdate(
                isOneTimeRequest = true,
                updateFrequency = fetchFrequency.toLong()
            ).flatMapLatest { coordinatesWrapper ->
                Log.e(this::class.simpleName, "Coordinates are $coordinatesWrapper")
                weatherRepository.fetchCurrentLocationWeather(
                    coordinatesWrapper, true
                ).map { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Success -> ServiceFetchingResult.Success(networkResult.data!!)
                        is NetworkResult.Error -> ServiceFetchingResult.Error(networkResult.message.orEmpty())
                        is NetworkResult.Loading -> ServiceFetchingResult.Loading("Fetching result from server")
                    }
                }
            }
        }
}