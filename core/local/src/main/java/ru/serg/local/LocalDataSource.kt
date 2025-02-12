package ru.serg.local

import kotlinx.coroutines.flow.Flow
import ru.serg.model.AlertItem
import ru.serg.model.CityItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.WeatherItem

interface LocalDataSource {
    fun getWeatherFlow(): Flow<List<WeatherItem>>

    fun saveWeather(
        hourlyWeatherList: List<HourlyWeather>,
        dailyWeatherList: List<DailyWeather>,
        alertList: List<AlertItem>,
        cityItem: CityItem
    )


    suspend fun deleteCityItemHistorySearch(cityItem: CityItem)

    suspend fun insertCityItemToSearchHistory(cityItem: CityItem)

    fun getFavouriteCity(): Flow<CityItem>

    fun getCitySearchHistory(): Flow<List<CityItem>>

    fun getFavouriteCityWeather(): Flow<WeatherItem>

    suspend fun cleanOutdatedWeather()
}