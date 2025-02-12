package ru.serg.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.serg.database.room.dao.CityDao
import ru.serg.database.room.dao.WeatherDao
import ru.serg.database.toAlertEntity
import ru.serg.database.toCityEntity
import ru.serg.database.toCityItem
import ru.serg.database.toDailyWeatherEntity
import ru.serg.database.toHourlyWeatherEntity
import ru.serg.database.toWeatherItem
import ru.serg.model.AlertItem
import ru.serg.model.CityItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.WeatherItem
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val cityDao: CityDao
) : LocalDataSource {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getWeatherFlow(): Flow<List<WeatherItem>> {

        return weatherDao.getWeatherWithCity().map { list ->
            list.map {
                it.toWeatherItem()
            }.also { updatedWeatherItems ->
                updatedWeatherItems.map {
                    it.dailyWeatherList.filter { dailyWeather ->
                        dailyWeather.dateTime > System.currentTimeMillis()
                    }

                    it.hourlyWeatherList.filter { hourlyWeather ->
                        hourlyWeather.dateTime > System.currentTimeMillis()
                    }
                }

                scope.launch {
                    weatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
                }
            }
        }.distinctUntilChanged()
    }

    override suspend fun cleanOutdatedWeather() {
        weatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
    }

    override fun saveWeather(
        hourlyWeatherList: List<HourlyWeather>,
        dailyWeatherList: List<DailyWeather>,
        alertList: List<AlertItem>,
        cityItem: CityItem
    ) {
        scope.launch {

            weatherDao.saveWeather(
                hourlyWeatherList.map { it.toHourlyWeatherEntity(cityItem.id) },
                dailyWeatherList.map { it.toDailyWeatherEntity(cityItem.id) },
                alertList.map { it.toAlertEntity(cityItem.id) },
                cityItem.toCityEntity()
            )
            weatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }
    }


    override suspend fun deleteCityItemHistorySearch(cityItem: CityItem) {
        weatherDao.deleteWeather(cityItem.id)
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

    override fun getFavouriteCityWeather(): Flow<WeatherItem> {
        return getWeatherFlow().map {
            it.firstOrNull { updatedWeatherItem ->
                updatedWeatherItem.cityItem.isFavorite
            } ?: it.first()
        }.distinctUntilChanged()
    }
}