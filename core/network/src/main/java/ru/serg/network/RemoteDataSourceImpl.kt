package ru.serg.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.serg.datastore.DataStoreDataSource
import ru.serg.model.enums.Units
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

    override fun getOneCallWeather(lat: Double, lon: Double): Flow<OneCallResponse> =
        flow {
            dataStoreDataSource.measurementUnits.collect { value ->
                val units = Units.entries[value].parameterCode
                emit(
                    httpClientOneCall.get {
                        parameter("units", units)
                        parameter("exclude", "minutely")
                        parameter("lon", lon)
                        parameter("lat", lat)
                    }.body()
                )
            }
        }

    override fun getWeather(lat: Double, lon: Double): Flow<WeatherResponse> =
        flow {

            dataStoreDataSource.measurementUnits.collect { value ->
                val units = Units.entries[value].parameterCode
                emit(
                    httpClientWeather.get {
                        parameter("units", units)
                        parameter("exclude", "minutely")
                        parameter("lon", lon)
                        parameter("lat", lat)
                    }.body()
                )
            }

        }

    override fun getCityForAutocomplete(input: String?): Flow<List<CityNameGeocodingResponseItem>> =
        flow {
            emit(httpClientGeocoding.get {
                parameter("q", input)
                parameter("limit", 15)
            }.body())
        }
}