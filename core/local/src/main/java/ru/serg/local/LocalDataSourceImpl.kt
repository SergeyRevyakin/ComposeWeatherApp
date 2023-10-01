package ru.serg.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.serg.database.room.dao.CityDao
import ru.serg.database.room.dao.WeatherDao
import ru.serg.database.room.entity.toCityItem
import ru.serg.database.toDailyWeatherEntity
import ru.serg.database.toHourlyWeatherEntity
import ru.serg.database.toWeatherItem
import ru.serg.model.CityItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.UpdatedWeatherItem
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val cityDao: CityDao
) : LocalDataSource {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getWeatherFlow(): Flow<List<UpdatedWeatherItem>> {
        scope.launch {
            weatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }

        return weatherDao.getWeatherWithCity().map { list ->
            list.map {
                it.toWeatherItem()
            }
        }
    }

    override fun saveWeather(
        hourlyWeatherList: List<HourlyWeather>,
        dailyWeatherList: List<DailyWeather>,
        cityItem: CityItem
    ) {
        scope.launch {

            hourlyWeatherList.map {

            }

            weatherDao.saveWeather(
                hourlyWeatherList.map { it.toHourlyWeatherEntity(cityItem.id) },
                dailyWeatherList.map { it.toDailyWeatherEntity(cityItem.id) },
                cityItem.toCityEntity()
            )
            weatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }
    }


    override suspend fun deleteCityItemToHistorySearch(cityItem: CityItem) {
        cityDao.deleteCityFromHistory(cityItem.toCityEntity())
    }

    override suspend fun insertCityItemToSearchHistory(cityItem: CityItem) {
        cityDao.addCityToHistory(
            cityItem.toCityEntity()
        )
    }

    override fun getFavouriteCity(): Flow<CityItem> {
        return cityDao.citySearchHistoryFlow().map { list ->
            list.map { entity ->
                entity.toCityItem()
            }.find {
                it.isFavorite
            } ?: list.first().toCityItem()
        }.distinctUntilChanged()
    }

    override fun getCitySearchHistory() = cityDao.citySearchHistoryFlow().map { entities ->
        entities.filter { !it.isFavorite }
            .map { cityEntity ->
                cityEntity.toCityItem()
            }
    }.distinctUntilChanged()

}