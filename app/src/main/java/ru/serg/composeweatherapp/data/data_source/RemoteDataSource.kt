package ru.serg.composeweatherapp.data.data_source

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.serg.composeweatherapp.data.remote.responses.CityNameGeocodingResponseItem
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject
import javax.inject.Named

class RemoteDataSource @Inject constructor(
    @Named(Constants.WEATHER) private val httpClientWeather: HttpClient,
    @Named(Constants.ONECALL) private val httpClientOneCall: HttpClient,
    @Named(Constants.GEOCODING) private val httpClientGeocoding: HttpClient,
    private val dataStoreDataSource: DataStoreDataSource
): IRemoteDataSource  {

    override fun getOneCallWeather(lat: Double, lon: Double): Flow<NetworkResult<OneCallResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                dataStoreDataSource.measurementUnits.collect { value ->
                    val units = Constants.DataStore.Units.values()[value].parameterCode
                    httpClientOneCall.get {
                        parameter("units", units)
                        parameter("exclude", "minutely")
                        parameter("lon", lon)
                        parameter("lat", lat)
                    }.let {
                        if (it.status.value == 200) {
                            emit(NetworkResult.Success(it.body()))
                        } else {
                            emit(NetworkResult.Error(data = it.body(), message = ""))
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
                emit(NetworkResult.Loading())
                dataStoreDataSource.measurementUnits.collect { value ->
                    val units = Constants.DataStore.Units.values()[value].parameterCode
                    httpClientWeather.get {
                        parameter("units", units)
                        parameter("exclude", "minutely")
                        parameter("lon", lon)
                        parameter("lat", lat)
                    }.let {
                        if (it.status.value == 200) {
                            emit(NetworkResult.Success(it.body()))
                        } else {
                            emit(NetworkResult.Error(data = it.body(), message = ""))
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
                emit(NetworkResult.Loading())
                httpClientGeocoding.get {
                    parameter("q", input)
                    parameter("limit", 15)
                }.let {
                    if (it.status.value == 200) {
                        emit(NetworkResult.Success(it.body()))
                    } else {
                        emit(NetworkResult.Error(data = it.body(), message = ""))
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.localizedMessage))
            }
        }
    }
}