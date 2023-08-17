package ru.serg.network

import kotlinx.coroutines.flow.Flow
import ru.serg.common.NetworkResult
import ru.serg.dto.CityNameGeocodingResponseItem
import ru.serg.dto.OneCallResponse
import ru.serg.dto.WeatherResponse

interface RemoteDataSource {
    fun getOneCallWeather(lat: Double, lon: Double): Flow<NetworkResult<OneCallResponse>>

    fun getWeather(lat: Double, lon: Double): Flow<NetworkResult<WeatherResponse>>

    fun getCityForAutocomplete(input: String?): Flow<NetworkResult<List<CityNameGeocodingResponseItem>>>
}