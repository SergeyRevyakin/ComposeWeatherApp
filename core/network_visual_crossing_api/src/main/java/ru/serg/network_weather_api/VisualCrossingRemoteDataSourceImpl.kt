package ru.serg.network_weather_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.serg.network_weather_api.dto.VisualCrossingResponse
import javax.inject.Inject

class VisualCrossingRemoteDataSourceImpl @Inject constructor(
    private val httpClientOneCall: HttpClient,
) : VisualCrossingRemoteDataSource {

    override fun getVisualCrossingForecast(lat: Double, lon: Double): Flow<VisualCrossingResponse> =
        flow {
            emit(
                httpClientOneCall.get() {
                    url {
                        path("$lat,$lon")
                    }
                }.body()
            )
        }

}