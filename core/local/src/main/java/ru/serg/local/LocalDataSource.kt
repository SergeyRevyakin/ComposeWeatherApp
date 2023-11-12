package ru.serg.local

import kotlinx.coroutines.flow.Flow
import ru.serg.model.CityItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.UpdatedWeatherItem

interface LocalDataSource {
    fun getWeatherFlow(): Flow<List<UpdatedWeatherItem>>

    fun saveWeather(
        hourlyWeatherList: List<HourlyWeather>,
        dailyWeatherList: List<DailyWeather>,
        cityItem: CityItem
    )


    suspend fun deleteCityItemToHistorySearch(cityItem: CityItem)

    suspend fun insertCityItemToSearchHistory(cityItem: CityItem)

    fun getFavouriteCity(): Flow<CityItem>

    fun getCitySearchHistory(): Flow<List<CityItem>>

    fun getFavouriteCityWeather(): Flow<UpdatedWeatherItem>
}