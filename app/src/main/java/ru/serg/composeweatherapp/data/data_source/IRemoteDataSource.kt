package ru.serg.composeweatherapp.data.data_source

import kotlinx.coroutines.flow.Flow
import ru.serg.composeweatherapp.data.remote.responses.CityNameGeocodingResponseItem
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.utils.NetworkResult

interface IRemoteDataSource {
    fun getOneCallWeather(lat: Double, lon: Double): Flow<NetworkResult<OneCallResponse>>

    fun getWeather(lat: Double, lon: Double): Flow<NetworkResult<WeatherResponse>>

    fun getCityForAutocomplete(input: String?): Flow<NetworkResult<List<CityNameGeocodingResponseItem>>>
}