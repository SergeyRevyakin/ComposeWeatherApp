package ru.serg.network_weather_api

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
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val BASE_URL = "weather.visualcrossing.com"
        const val FORECAST = "/VisualCrossingWebServices/rest/services/timeline/"
        const val API_KEY_PARAM_NAME = "key"
        const val UNIT_GROUP_PARAM_NAME = "unitGroup"
        const val UNIT_GROUP_PARAM_VALUE = "metric"
        const val LANG = "lang"
    }

    @Singleton
    @Provides
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

            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            defaultRequest {
                host = BASE_URL
                url {
                    protocol = URLProtocol.HTTPS
                    appendPathSegments(FORECAST)
                    parameters.apply {
                        append(API_KEY_PARAM_NAME, BuildConfig.VISUAL_CROSSING_API_KEY)
                        append(UNIT_GROUP_PARAM_NAME, UNIT_GROUP_PARAM_VALUE)
                        append(LANG, Locale.getDefault().language)
                    }
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}
