package com.serg.network_self_proxy

import com.serg.network_self_proxy.dto.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelfProxyRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : SelfProxyRemoteDataSource {

    override fun getSelfProxyForecast(lat: Double, lon: Double): Flow<WeatherResponse> =
        flow {
            emit(
                httpClient.get() {
                    url {
                        parameter("lon", lon)
                        parameter("lat", lat)
                    }
                }.body()
            )
        }

}