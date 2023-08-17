@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.data

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.serg.composeweatherapp.utils.ServiceFetchingResult
import ru.serg.datastore.DataStoreDataSource
import ru.serg.model.WeatherItem
import javax.inject.Inject

class WeatherServiceUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationService: ru.serg.location.LocationDataSource,
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
                ).map { networkResult ->
                    when (networkResult) {
                        is ru.serg.common.NetworkResult.Success -> ServiceFetchingResult.Success(
                            networkResult.data!!
                        )

                        is ru.serg.common.NetworkResult.Error -> ServiceFetchingResult.Error(
                            networkResult.message.orEmpty()
                        )

                        is ru.serg.common.NetworkResult.Loading -> ServiceFetchingResult.Loading("Fetching result from server")
                    }
                }
            }
        }
}