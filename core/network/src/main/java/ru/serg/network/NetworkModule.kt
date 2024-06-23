package ru.serg.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.Locale
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val BASE_URL_ONECALL = "api.openweathermap.org/data/2.5/onecall"
        const val BASE_URL_WEATHER = "api.openweathermap.org/data/2.5/weather"
        const val BASE_URL_GEOCODING = "api.openweathermap.org/geo/1.0/direct"
        const val BASE_URL_AIR_QUALITY = "api.openweathermap.org/data/2.5/air_pollution/forecast"
        const val WEATHER = "weather"
        const val ONECALL = "onecall"
        const val GEOCODING = "geo"
        const val AIR_QUALITY = "air_quality"
        const val APP_ID = "appid"
        const val LANG = "lang"
    }

    @Singleton
    @Provides
    @Named(ONECALL)
    fun provideHttpClient(): HttpClient {

        return HttpClient(Android) {

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
            install(Logging) {
                if (BuildConfig.DEBUG) {
                    logger = Logger.SIMPLE
                    level = LogLevel.BODY
                } else {
                    logger = Logger.EMPTY
                    level = LogLevel.NONE
                }
            }
            expectSuccess = false
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            defaultRequest {
                host = BASE_URL_ONECALL
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append(APP_ID, BuildConfig.OWM_API_KEY)
                    parameters.append(LANG, Locale.getDefault().language)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    @Named(WEATHER)
    fun provideWeatherHttpClient(): HttpClient {

        return HttpClient(Android) {

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

            install(Logging) {
                if (BuildConfig.DEBUG) {
                    logger = Logger.SIMPLE
                    level = LogLevel.BODY
                } else {
                    logger = Logger.EMPTY
                    level = LogLevel.NONE
                }
            }
            expectSuccess = false
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            defaultRequest {
                host = BASE_URL_WEATHER
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append(APP_ID, BuildConfig.OWM_API_KEY)
                    parameters.append(LANG, Locale.getDefault().language)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    @Named(GEOCODING)
    fun provideGeocodingHttpClient(): HttpClient {

        return HttpClient(Android) {

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

            install(Logging) {
                if (BuildConfig.DEBUG) {
                    logger = Logger.SIMPLE
                    level = LogLevel.BODY
                } else {
                    logger = Logger.EMPTY
                    level = LogLevel.NONE
                }
            }
            expectSuccess = false
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            defaultRequest {
                host = BASE_URL_GEOCODING
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append(APP_ID, BuildConfig.OWM_API_KEY)
                    parameters.append(LANG, Locale.getDefault().language)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    @Named(AIR_QUALITY)
    fun provideAirQualityHttpClient(): HttpClient {

        return HttpClient(Android) {

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

            install(Logging) {
                if (BuildConfig.DEBUG) {
                    logger = Logger.SIMPLE
                    level = LogLevel.BODY
                } else {
                    logger = Logger.EMPTY
                    level = LogLevel.NONE
                }
            }
            expectSuccess = false
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            defaultRequest {
                host = BASE_URL_AIR_QUALITY
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append(APP_ID, BuildConfig.OWM_API_KEY)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}
