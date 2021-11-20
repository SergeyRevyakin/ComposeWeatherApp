package ru.serg.composeweatherapp.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getWeather(): WeatherResult.Success {
        return httpClient.get {
            url.path("onecall")
            parameter("exclude", "minutely")
            parameter("lon", 10.1257)
            parameter("lat", 51.5085)
        }
    }
}