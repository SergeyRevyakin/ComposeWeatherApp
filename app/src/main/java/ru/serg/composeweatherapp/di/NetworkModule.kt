package ru.serg.composeweatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import ru.serg.composeweatherapp.BuildConfig
import ru.serg.composeweatherapp.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            explicitNulls = false
            coerceInputValues = true
        }

        return HttpClient {
            // setups the json deserializer
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
            // setups the logging (android studio profiler not works with this library)
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
//                socketTimeoutMillis = 15_000
                requestTimeoutMillis = 5_000
//                connectTimeoutMillis = 15_000
            }
//            install(ParametersBuilder)
            defaultRequest {
                host = Constants.BASE_URL
                url {
                    protocol = URLProtocol.HTTPS
                    parameter("units", "metric")
                    parameter("appid", BuildConfig.OWM_API_KEY)
                }

                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }

}