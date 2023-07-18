package ru.serg.composeweatherapp.data.data_source

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.data.dto.UpdatedWeatherItem
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.room.dao.UpdatedWeatherDao
import ru.serg.composeweatherapp.data.room.entity.UpdatedDailyWeatherEntity
import ru.serg.composeweatherapp.data.room.entity.UpdatedHourlyWeatherEntity
import ru.serg.composeweatherapp.utils.IconMapper
import ru.serg.composeweatherapp.utils.toCityEntity
import ru.serg.composeweatherapp.utils.toTimeStamp
import ru.serg.composeweatherapp.utils.toWeatherItem
import javax.inject.Inject

class UpdatedLocalDataSource @Inject constructor(
    private val dailyWeatherDao: UpdatedWeatherDao,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getWeatherFlow(): Flow<List<UpdatedWeatherItem>> {
        scope.launch {
            dailyWeatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }

        return dailyWeatherDao.getWeatherWithCity().map { list ->
            list.map {
                it.toWeatherItem()
            }
        }
    }

    fun saveWeather(oneCallResponse: OneCallResponse, cityItem: CityItem) {
        scope.launch {
            val dailyWeather = oneCallResponse.daily?.map {

                UpdatedDailyWeatherEntity(
                    windDirection = it.windDeg,
                    windSpeed = it.windSpeed,
                    weatherDescription = it.weather?.first()?.description,
                    weatherIcon = IconMapper.map(it.weather?.first()?.id),
                    dateTime = it.dt.toTimeStamp(),
                    humidity = it.humidity,
                    pressure = it.pressure,
                    feelsLike = DataMapper.getFeelsLikeDailyTempItem(it),
                    dailyWeatherItem = DataMapper.getUpdatedDailyTempItem(it),
                    cityId = cityItem.id,
                    sunset = it.sunset.toTimeStamp(),
                    sunrise = it.sunrise.toTimeStamp()
                )
            } ?: listOf()

            val hourlyWeather = oneCallResponse.hourly?.map {
                UpdatedHourlyWeatherEntity(
                    windDirection = it.windDeg,
                    windSpeed = it.windSpeed,
                    weatherDescription = it.weather?.first()?.description,
                    weatherIcon = IconMapper.map(it.weather?.first()?.id),
                    dateTime = it.dt.toTimeStamp(),
                    humidity = it.humidity,
                    pressure = it.pressure,
                    cityId = cityItem.id,
                    currentTemp = it.temp,
                    feelsLike = it.feelsLike
                )
            } ?: listOf()

            dailyWeatherDao.saveWeather(hourlyWeather, dailyWeather, cityItem.toCityEntity())
            dailyWeatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }
    }

}