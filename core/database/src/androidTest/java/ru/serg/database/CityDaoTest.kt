package ru.serg.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.serg.database.room.AppDatabase
import ru.serg.database.room.dao.CityDao
import ru.serg.database.room.entity.CityEntity
import kotlin.test.assertEquals

class CityDaoTest {

    private lateinit var database: AppDatabase

    private lateinit var cityDao: CityDao


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        cityDao = database.cityDao()
    }

    @Test
    fun add_one_city() = runTest {
        cityDao.addCityToHistory(getFixedIdTestCityEntity())
        val result = cityDao.citySearchHistoryFlow().first()
        assertEquals(
            listOf(getFixedIdTestCityEntity()),
            result
        )
    }

    @Test
    fun add_multiple_cities() = runTest {
        val cityList = mutableListOf<CityEntity>()

        for (i in 1..5) {
            val city = getTestCityEntity(i)
            cityList.add(city)
            cityDao.addCityToHistory(city)
        }

        val result = cityDao.citySearchHistoryFlow().first()

        assertEquals(
            cityList,
            result
        )
    }

    @Test
    fun update_city_quantity() = runTest {
        val cityEntity = getTestCityEntity(1)
        cityDao.addCityToHistory(cityEntity)
        cityEntity.lastTimeUpdated = 199L
        cityDao.addCityToHistory(cityEntity)

        val result = cityDao.citySearchHistoryFlow().first()

        assertEquals(1, result.size)
    }

    @Test
    fun update_city_element() = runTest {
        val cityEntity = getTestCityEntity(1)
        cityDao.addCityToHistory(cityEntity)
        cityEntity.lastTimeUpdated = 199L
        cityDao.addCityToHistory(cityEntity)

        val result = cityDao.citySearchHistoryFlow().first()

        assertEquals(cityEntity, result.first())
    }

    @Test
    fun delete_city() = runTest {
        val city = getFixedIdTestCityEntity()
        cityDao.addCityToHistory(city)
        val city1 = getTestCityEntity(6)
        cityDao.addCityToHistory(city1)

        cityDao.deleteCityFromHistory(city1)

        val result = cityDao.citySearchHistoryFlow().first()

        assertEquals(
            listOf(city),
            result
        )
    }

}

private fun getFixedIdTestCityEntity() = CityEntity(
    cityName = "London",
    country = "GB",
    isFavorite = false,
    latitude = 34.56,
    longitude = 56.67,
    lastTimeUpdated = 2L,
    id = 2
)

private fun getTestCityEntity(id: Int) = CityEntity(
    cityName = "London",
    country = "GB",
    isFavorite = false,
    latitude = 34.56,
    longitude = 56.67,
    lastTimeUpdated = 2L,
    id = id
)
