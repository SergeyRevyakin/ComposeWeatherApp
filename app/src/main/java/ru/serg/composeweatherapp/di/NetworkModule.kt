package ru.serg.composeweatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.serg.composeweatherapp.BuildConfig
import ru.serg.composeweatherapp.utils.Constants
import java.nio.charset.Charset
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {

        return HttpClient(Android) {
            // setups the json deserializer

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
            expectSuccess = false
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
//            install(ParametersBuilder)
            defaultRequest {
                host = Constants.BASE_URL
                url {
                    protocol = URLProtocol.HTTPS
                    parameters.append("units", "metric")
                    parameters.append("appid", BuildConfig.OWM_API_KEY)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                contentType(ContentType.Application.Json)
//                accept(ContentType.Application.Json)
            }
        }
    }

}