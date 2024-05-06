package ru.serg.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.serg.database.room.AppDatabase
import ru.serg.database.room.dao.WeatherDao
import ru.serg.database.room.entity.CityEntity
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity
import ru.serg.model.DailyTempItem

private lateinit var weatherDatabase: AppDatabase
private lateinit var weatherDao: WeatherDao

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        weatherDao = weatherDatabase.weatherDao()
    }

    @After
    fun tearDown() {
        weatherDatabase.close()
    }

    @Test
    fun save_and_retrieve_weather_data() = runTest {

        // When
        weatherDao.saveWeather(
            listOf(hourlyWeatherEntity),
            listOf(dailyWeatherEntity),
            cityEntity
        )

        val savedCityWeather = weatherDao.getWeatherWithCity().first()

        // Then
        assertEquals(1, savedCityWeather.size)
        assertEquals(cityEntity.cityName, savedCityWeather[0].cityEntity.cityName)
        assertEquals(1, savedCityWeather[0].hourlyWeatherEntity.size)
        assertEquals(1, savedCityWeather[0].dailyWeatherEntity.size)
    }

    @Test
    fun save_and_retrieve_weather_data_two_items() = runTest {

        // When
        weatherDao.saveWeather(
            listOf(hourlyWeatherEntity),
            listOf(dailyWeatherEntity),
            cityEntity
        )
        weatherDao.saveWeather(
            listOf(hourlyWeatherEntity.copy(dateTime = 212)),
            listOf(dailyWeatherEntity.copy(dateTime = 212)),
            cityEntity
        )


        val savedCityWeather = weatherDao.getWeatherWithCity().first()

        // Then
        assertEquals(1, savedCityWeather.size)
        assertEquals(cityEntity.cityName, savedCityWeather[0].cityEntity.cityName)
        assertEquals(2, savedCityWeather[0].hourlyWeatherEntity.size)
        assertEquals(2, savedCityWeather[0].dailyWeatherEntity.size)
    }

    @Test
    fun delete_city_with_weather_zero_expected() = runTest {
        // Given
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120),
                hourlyWeatherEntity.copy(dateTime = 124)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120),
                dailyWeatherEntity.copy(dateTime = 124)
            ),
            cityEntity.copy(lastTimeUpdated = 120)
        )

        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 1200),
                hourlyWeatherEntity.copy(dateTime = 1240)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 1200),
                dailyWeatherEntity.copy(dateTime = 1240)
            ),
            cityEntity.copy(lastTimeUpdated = 1200)
        )

        // When
        weatherDao.deleteWeather(cityEntity.id)

        // Then
        val deletedCityWeather = weatherDao.getWeatherWithCity().first()
        assertEquals(0, deletedCityWeather.size)

    }

    @Test
    fun delete_city_with_weather_one_expected() = runTest {
        // Given
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120),
                hourlyWeatherEntity.copy(dateTime = 124)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120),
                dailyWeatherEntity.copy(dateTime = 124)
            ),
            cityEntity.copy(lastTimeUpdated = 120)
        )

        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 1200),
                hourlyWeatherEntity.copy(dateTime = 1240)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 1200),
                dailyWeatherEntity.copy(dateTime = 1240)
            ),
            cityEntity.copy(lastTimeUpdated = 1200)
        )

        val newCityId = 123
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120, cityId = newCityId),
                hourlyWeatherEntity.copy(dateTime = 124, cityId = newCityId)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120, cityId = newCityId),
                dailyWeatherEntity.copy(dateTime = 124, cityId = newCityId)
            ),
            cityEntity.copy(lastTimeUpdated = 120, id = newCityId)
        )

        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 1200, cityId = newCityId),
                hourlyWeatherEntity.copy(dateTime = 1240, cityId = newCityId)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 1200, cityId = newCityId),
                dailyWeatherEntity.copy(dateTime = 1240, cityId = newCityId)
            ),
            cityEntity.copy(lastTimeUpdated = 1200, id = newCityId)
        )

        // When
        weatherDao.deleteWeather(cityEntity.id)

        // Then
        val deletedCityWeather = weatherDao.getWeatherWithCity().first()
        assertEquals(1, deletedCityWeather.size)
        assertEquals(cityEntity.cityName, deletedCityWeather[0].cityEntity.cityName)
        assertEquals(newCityId, deletedCityWeather[0].cityEntity.id)
        assertEquals(4, deletedCityWeather[0].hourlyWeatherEntity.size)
        assertEquals(4, deletedCityWeather[0].dailyWeatherEntity.size)
    }

    @Test
    fun delete_outdated_city_weather_zero_expected() = runTest {
        // Given
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120),
                hourlyWeatherEntity.copy(dateTime = 124)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120),
                dailyWeatherEntity.copy(dateTime = 124)
            ),
            cityEntity.copy(lastTimeUpdated = 120)
        )

        // When
        weatherDao.cleanupOutdatedWeather(126)

        // Then
        val deletedCityWeather = weatherDao.getWeatherWithCity().first()
        val hourlyWeatherList = deletedCityWeather.first().hourlyWeatherEntity
        val dailyWeatherList = deletedCityWeather.first().dailyWeatherEntity
        assertEquals(0, hourlyWeatherList.size)
        assertEquals(0, dailyWeatherList.size)
    }

    @Test
    fun delete_outdated_city_weather_one_expected() = runTest {
        // Given
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120),
                hourlyWeatherEntity.copy(dateTime = 124)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120),
                dailyWeatherEntity.copy(dateTime = 124)
            ),
            cityEntity.copy(lastTimeUpdated = 120)
        )

        // When
        weatherDao.cleanupOutdatedWeather(122)

        // Then
        val deletedCityWeather = weatherDao.getWeatherWithCity().first()
        val hourlyWeatherList = deletedCityWeather.first().hourlyWeatherEntity
        val dailyWeatherList = deletedCityWeather.first().dailyWeatherEntity
        assertEquals(1, hourlyWeatherList.size)
        assertEquals(1, dailyWeatherList.size)
    }

    @Test
    fun delete_outdated_city_weather_two_expected() = runTest {
        // Given
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120),
                hourlyWeatherEntity.copy(dateTime = 124)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120),
                dailyWeatherEntity.copy(dateTime = 124)
            ),
            cityEntity.copy(lastTimeUpdated = 12)
        )

        // When
        weatherDao.cleanupOutdatedWeather(122)

        // Then
        val deletedCityWeather = weatherDao.getWeatherWithCity().first()
        val hourlyWeatherList = deletedCityWeather.first().hourlyWeatherEntity
        val dailyWeatherList = deletedCityWeather.first().dailyWeatherEntity
        assertEquals(1, hourlyWeatherList.size)
        assertEquals(1, dailyWeatherList.size)
    }

    @Test
    fun put_new_favourite_city() = runTest {
        // Given
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120),
                hourlyWeatherEntity.copy(dateTime = 124)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120),
                dailyWeatherEntity.copy(dateTime = 124)
            ),
            cityEntity.copy(lastTimeUpdated = 12, id = -1)
        )

        // When
        weatherDao.saveWeather(
            listOf(
                hourlyWeatherEntity.copy(dateTime = 120),
                hourlyWeatherEntity.copy(dateTime = 124)
            ),
            listOf(
                dailyWeatherEntity.copy(dateTime = 120),
                dailyWeatherEntity.copy(dateTime = 124)
            ),
            cityEntity.copy(lastTimeUpdated = 12, id = -1, cityName = "london")
        )

        // Then
        val deletedCityWeather = weatherDao.getWeatherWithCity().first().first()
        assertEquals("london", deletedCityWeather.cityEntity.cityName)
        assertEquals(-1, deletedCityWeather.cityEntity.id)
    }


}

val cityEntity = CityEntity(
    cityName = "Test City",
    country = "Test Country",
    latitude = 0.0,
    longitude = 0.0,
    lastTimeUpdated = System.currentTimeMillis(),
    id = 1
)

val hourlyWeatherEntity = HourlyWeatherEntity(
    feelsLike = 25.0,
    currentTemp = 27.0,
    windDirection = 180,
    windSpeed = 5.0,
    humidity = 70,
    pressure = 1015,
    weatherDescription = "Sunny",
    weatherIcon = 123,
    dateTime = System.currentTimeMillis(),
    cityId = 1,
    uvi = 6.0
)

val dailyWeatherEntity = DailyWeatherEntity(
    windDirection = 180,
    windSpeed = 5.0,
    humidity = 70,
    pressure = 1015,
    weatherDescription = "Sunny",
    weatherIcon = 123,
    dateTime = System.currentTimeMillis(),
    sunrise = System.currentTimeMillis(),
    sunset = System.currentTimeMillis(),
    cityId = 1,
    dailyWeatherItem = DailyTempItem(25.0, 20.0, 20.0, 20.0, 20.0, 16.0),
    feelsLike = DailyTempItem(25.0, 20.0, 20.0, 20.0, 20.0, 16.0),
    uvi = 6.0
)