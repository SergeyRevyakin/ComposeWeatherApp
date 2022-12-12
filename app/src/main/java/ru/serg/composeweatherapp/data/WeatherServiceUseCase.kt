@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.data.data_source.LocationServiceImpl
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ServiceFetchingResult
import javax.inject.Inject

class WeatherServiceUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationServiceImpl,
    private val dataStoreDataSource: DataStoreDataSource
) {
    fun checkCurrentLocationAndWeather(): Flow<ServiceFetchingResult<WeatherItem>> =

        dataStoreDataSource.fetchFrequency.flatMapLatest { fetchFrequency ->
            locationService.getLocationUpdate(
                isOneTimeRequest = true,
                updateFrequency = fetchFrequency.toLong()
            ).flatMapLatest { coordinatesWrapper ->
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