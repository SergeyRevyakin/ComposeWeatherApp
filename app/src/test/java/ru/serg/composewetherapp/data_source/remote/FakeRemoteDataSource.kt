package ru.serg.composewetherapp.data_source.remote

import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.utils.NetworkResult

//@HiltAndroidTest
//@RunWith(JUnit4::class)
class FakeRemoteDataSource {

//    @get:Rule
//    val rule = HiltAndroidRule(this)

    private val remoteDataSource = RemoteDataSource(
        FakeNetworkModule().provideHttpWeatherClient(),
        FakeNetworkModule().provideHttpOneCallClient(),
        FakeNetworkModule().provideHttpCityClient(),
    )

    @Test
    fun testGetWeather() {
        runBlocking {
            val res = remoteDataSource.getWeather(0.0, 0.0)
            assertEquals(NetworkResult.Success::class, res.last()::class)

        }
    }

    @Test
    fun testGetOneCall() {
        runBlocking {
            val res = remoteDataSource.getOneCallWeather(0.0, 0.0)
            assertEquals(NetworkResult.Success::class, res.last()::class)

        }
    }

    @Test
    fun testGetCity() {
        runBlocking {
            val res = remoteDataSource.getCityForAutocomplete("")
            assertEquals(NetworkResult.Success::class, res.last()::class)

        }
    }
}