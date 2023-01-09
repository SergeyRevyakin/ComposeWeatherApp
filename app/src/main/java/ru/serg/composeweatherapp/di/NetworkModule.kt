package ru.serg.composeweatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import ru.serg.composeweatherapp.BuildConfig
import ru.serg.composeweatherapp.utils.Constants
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    @Named(Constants.ONECALL)
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
                host = Constants.BASE_URL_ONECALL
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append("appid", BuildConfig.OWM_API_KEY)
                    parameters.append("lang", Locale.getDefault().country)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    @Named(Constants.WEATHER)
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
                host = Constants.BASE_URL_WEATHER
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append("appid", BuildConfig.OWM_API_KEY)
                    parameters.append("lang", Locale.getDefault().country)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    @Named(Constants.GEOCODING)
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
                host = Constants.BASE_URL_GEOCODING
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append("appid", BuildConfig.OWM_API_KEY)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

}