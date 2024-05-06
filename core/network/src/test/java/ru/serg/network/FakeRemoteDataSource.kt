package ru.serg.network

import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.serg.network.dto.AirQualityResponse
import ru.serg.network.dto.CityNameGeocodingResponseItem
import ru.serg.network.dto.OneCallResponse
import ru.serg.network.dto.WeatherResponse
import kotlin.test.assertEquals

class FakeRemoteDataSource {
    private val remoteDataSource = RemoteDataSourceImpl(
        FakeNetworkModule().provideHttpWeatherClient(),
        FakeNetworkModule().provideHttpOneCallClient(),
        FakeNetworkModule().provideHttpCityClient(),
        FakeNetworkModule().provideHttpAirQualityClient(),
        FakeDataStoreDataSource()
    )

    @Test
    fun testGetWeather() {
        runTest {
            val res = remoteDataSource.getWeather(0.0, 0.0)
            assertEquals(WeatherResponse::class, res.last()::class)

        }
    }

    @Test
    fun testGetOneCall() {
        runTest {
            val res = remoteDataSource.getOneCallWeather(0.0, 0.0)
            assertEquals(OneCallResponse::class, res.last()::class)

        }
    }

    @Test
    fun testGetCity() {
        runTest {
            val res = remoteDataSource.getCityForAutocomplete(" ")
            assertEquals(CityNameGeocodingResponseItem::class, res.last().first()::class)

        }
    }

    @Test
    fun testAirQuality() {
        runTest {
            val res = remoteDataSource.getAirQuality(0.0, 0.0)
            assertEquals(AirQualityResponse::class, res.last()::class)

        }
    }
}