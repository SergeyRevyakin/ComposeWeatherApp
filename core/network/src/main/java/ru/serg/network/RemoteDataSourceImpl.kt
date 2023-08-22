package ru.serg.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.serg.common.NetworkResult
import ru.serg.common.Units
import ru.serg.datastore.DataStoreDataSource
import ru.serg.network.dto.CityNameGeocodingResponseItem
import ru.serg.network.dto.OneCallResponse
import ru.serg.network.dto.WeatherResponse
import javax.inject.Inject
import javax.inject.Named

class RemoteDataSourceImpl @Inject constructor(
    @Named(NetworkModule.WEATHER) private val httpClientWeather: HttpClient,
    @Named(NetworkModule.ONECALL) private val httpClientOneCall: HttpClient,
    @Named(NetworkModule.GEOCODING) private val httpClientGeocoding: HttpClient,
    private val dataStoreDataSource: DataStoreDataSource
) : RemoteDataSource {

    override fun getOneCallWeather(lat: Double, lon: Double): Flow<NetworkResult<OneCallResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading)
                dataStoreDataSource.measurementUnits.collect { value ->
                    val units = Units.values()[value].parameterCode
                    httpClientOneCall.get {
                        parameter("units", units)
                        parameter("exclude", "minutely")
                        parameter("lon", lon)
                        parameter("lat", lat)
                    }.let {
                        if (it.status.value == 200) {
                            emit(NetworkResult.Success(it.body()))
                        } else {
                            emit(NetworkResult.Error(message = it.status.description))
                        }
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.localizedMessage))
            }
        }
    }

    override fun getWeather(lat: Double, lon: Double): Flow<NetworkResult<WeatherResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading)
                dataStoreDataSource.measurementUnits.collect { value ->
                    val units = Units.values()[value].parameterCode
                    httpClientWeather.get {
                        parameter("units", units)
                        parameter("exclude", "minutely")
                        parameter("lon", lon)
                        parameter("lat", lat)
                    }.let {
                        if (it.status.value == 200) {
                            emit(NetworkResult.Success(it.body()))
                        } else {
                            emit(NetworkResult.Error(message = it.status.description))
                        }
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.localizedMessage))
            }
        }
    }

    override fun getCityForAutocomplete(input: String?): Flow<NetworkResult<List<CityNameGeocodingResponseItem>>> {
        return flow {
            try {
                emit(NetworkResult.Loading)
                httpClientGeocoding.get {
                    parameter("q", input)
                    parameter("limit", 15)
                }.let {
                    if (it.status.value == 200) {
                        emit(NetworkResult.Success(it.body()))
                    } else {
                        emit(NetworkResult.Error(message = it.status.description))
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.localizedMessage))
            }
        }
    }
}