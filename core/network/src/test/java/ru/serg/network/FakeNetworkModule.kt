@file:OptIn(ExperimentalSerializationApi::class)

package ru.serg.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


class FakeNetworkModule {

    fun provideHttpWeatherClient(): HttpClient {

        val engine = MockEngine {
            respond(
                content = ByteReadChannel(ResponseConstants.WEATHER_RESPONSE),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )

        }

        val httpClient = HttpClient(
            engine = engine
        ) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    explicitNulls = false
                })
            }
            expectSuccess = false
        }
        return httpClient
    }

    fun provideHttpOneCallClient(): HttpClient {

        val engine = MockEngine {
            respond(
                content = ResponseConstants.ONECALL_RESPONSE,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(
            engine = engine
        ) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    explicitNulls = false
                })
            }
            expectSuccess = false
        }
        return httpClient
    }

    fun provideHttpCityClient(): HttpClient {

        val engine = MockEngine {
            respond(
                content = ResponseConstants.CITY_RESPONSE,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )

        }

        val httpClient = HttpClient(
            engine = engine
        ) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    explicitNulls = false
                })
            }
            expectSuccess = false
        }
        return httpClient
    }

    fun provideHttpAirQualityClient(): HttpClient {

        val engine = MockEngine {
            respond(
                content = ResponseConstants.AIR_QUALITY_RESPONSE,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(
            engine = engine
        ) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    explicitNulls = false
                })
            }
            expectSuccess = false
        }
        return httpClient
    }

}