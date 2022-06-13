package ru.serg.composeweatherapp.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getWeather(): WeatherResult {
        httpClient.get {
//            url.path("onecall")
            parameter("exclude", "minutely")
            parameter("lon", 10.1257)
            parameter("lat", 51.5085)
        }.apply {
            if (status.value == 200){
                return this.body() as WeatherResult.Success
            } else {
                return WeatherResult.Failure("error")
            }
        }
    }
}