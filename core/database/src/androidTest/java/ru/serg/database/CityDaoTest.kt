package ru.serg.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.serg.database.room.AppDatabase
import ru.serg.database.room.dao.CityHistorySearchDao
import ru.serg.database.room.entity.CityEntity
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CityDaoTest {

    private lateinit var database: AppDatabase

    private lateinit var cityHistorySearchDao: CityHistorySearchDao


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        cityHistorySearchDao = database.cityHistorySearchDao()
    }

    @Test
    fun add_one_city() = runTest {
        cityHistorySearchDao.addCityToHistory(getFixedIdTestCityEntity())
        val result = cityHistorySearchDao.citySearchHistoryFlow().first()
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
            cityHistorySearchDao.addCityToHistory(city)
        }

        val result = cityHistorySearchDao.citySearchHistoryFlow().first()

        assertEquals(
            cityList,
            result
        )
    }

    @Test
    fun update_city_quantity() = runTest {
        val cityEntity = getTestCityEntity(1)
        cityHistorySearchDao.addCityToHistory(cityEntity)
        cityEntity.lastTimeUpdated = 199L
        cityHistorySearchDao.addCityToHistory(cityEntity)

        val result = cityHistorySearchDao.citySearchHistoryFlow().first()

        assertEquals(1, result.size)
    }

    @Test
    fun update_city_element() = runTest {
        val cityEntity = getTestCityEntity(1)
        cityHistorySearchDao.addCityToHistory(cityEntity)
        cityEntity.lastTimeUpdated = 199L
        cityHistorySearchDao.addCityToHistory(cityEntity)

        val result = cityHistorySearchDao.citySearchHistoryFlow().first()

        assertEquals(cityEntity, result.first())
    }

    @Test
    fun delete_city() = runTest {
        val city = getFixedIdTestCityEntity()
        cityHistorySearchDao.addCityToHistory(city)
        val city1 = getTestCityEntity(6)
        cityHistorySearchDao.addCityToHistory(city1)

        cityHistorySearchDao.deleteCityFromHistory(city1)

        val result = cityHistorySearchDao.citySearchHistoryFlow().first()

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
