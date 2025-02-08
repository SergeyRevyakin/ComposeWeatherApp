package com.serg.network_self_proxy

import com.serg.network_self_proxy.dto.CityModel
import com.serg.network_self_proxy.dto.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface SelfProxyRemoteDataSource {
    fun getSelfProxyForecast(lat: Double, lon: Double): Flow<WeatherResponse>

    fun getCitiesForSearch(input: String?): Flow<List<CityModel>>
}