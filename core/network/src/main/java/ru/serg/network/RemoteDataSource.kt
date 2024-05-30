package ru.serg.network

import kotlinx.coroutines.flow.Flow
import ru.serg.network.dto.AirQualityResponse
import ru.serg.network.dto.CityNameGeocodingResponseItem
import ru.serg.network.dto.OneCallResponse
import ru.serg.network.dto.WeatherResponse

interface RemoteDataSource {

    fun getOneCallWeather(lat: Double, lon: Double): Flow<OneCallResponse>

    fun getWeather(lat: Double, lon: Double): Flow<WeatherResponse>

    fun getCityForAutocomplete(input: String?): Flow<List<CityNameGeocodingResponseItem>>

    fun getAirQuality(lat: Double, lon: Double): Flow<AirQualityResponse>
}