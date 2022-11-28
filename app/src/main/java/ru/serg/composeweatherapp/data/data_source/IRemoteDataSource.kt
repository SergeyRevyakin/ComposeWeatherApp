package ru.serg.composeweatherapp.data.data_source

import kotlinx.coroutines.flow.Flow
import ru.serg.composeweatherapp.data.remote.responses.CityNameGeocodingResponseItem
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.utils.NetworkResult

interface IRemoteDataSource {
    suspend fun getOneCallWeather(lat: Double, lon: Double): Flow<NetworkResult<OneCallResponse>>

    suspend fun getWeather(lat: Double, lon: Double): Flow<NetworkResult<WeatherResponse>>

    suspend fun getCityForAutocomplete(input: String?): Flow<NetworkResult<List<CityNameGeocodingResponseItem>>>
}