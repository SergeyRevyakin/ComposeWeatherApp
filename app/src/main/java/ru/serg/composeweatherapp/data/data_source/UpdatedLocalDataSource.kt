package ru.serg.composeweatherapp.data.data_source

import com.serg.model.CityItem
import com.serg.model.UpdatedWeatherItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.mapper.DataMapper
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.utils.toCityEntity
import ru.serg.composeweatherapp.utils.toTimeStamp
import ru.serg.composeweatherapp.utils.toWeatherItem
import ru.serg.composeweatherapp.utils.weather_mapper.IconMapper
import javax.inject.Inject

class UpdatedLocalDataSource @Inject constructor(
    private val dailyWeatherDao: com.serg.database.room.dao.UpdatedWeatherDao,
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

                com.serg.database.room.entity.UpdatedDailyWeatherEntity(
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
                    sunrise = it.sunrise.toTimeStamp(),
                    uvi = it.uvi
                )
            } ?: listOf()

            val hourlyWeather = oneCallResponse.hourly?.map {
                com.serg.database.room.entity.UpdatedHourlyWeatherEntity(
                    windDirection = it.windDeg,
                    windSpeed = it.windSpeed,
                    weatherDescription = it.weather?.first()?.description,
                    weatherIcon = IconMapper.map(it.weather?.first()?.id),
                    dateTime = it.dt.toTimeStamp(),
                    humidity = it.humidity,
                    pressure = it.pressure,
                    cityId = cityItem.id,
                    currentTemp = it.temp,
                    feelsLike = it.feelsLike,
                    uvi = it.uvi
                )
            } ?: listOf()

            dailyWeatherDao.saveWeather(hourlyWeather, dailyWeather, cityItem.toCityEntity())
            dailyWeatherDao.cleanupOutdatedWeather(System.currentTimeMillis())
        }
    }

}