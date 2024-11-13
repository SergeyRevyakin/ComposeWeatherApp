package ru.serg.network_weather_api

import kotlinx.coroutines.flow.Flow
import ru.serg.network_weather_api.dto.VisualCrossingResponse

interface VisualCrossingRemoteDataSource {

    fun getVisualCrossingForecast(lat: Double, lon: Double): Flow<VisualCrossingResponse>

//    fun getWeather(lat: Double, lon: Double): Flow<WeatherResponse>
//
//    fun getCityForAutocomplete(input: String?): Flow<List<CityNameGeocodingResponseItem>>
//
//    fun getAirQuality(lat: Double, lon: Double): Flow<AirQualityResponse>
}