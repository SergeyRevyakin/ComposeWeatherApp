package ru.serg.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import ru.serg.database.Constants
import ru.serg.model.AirQuality

@Entity(
    tableName = Constants.HOUR_WEATHER_TABLE,
    primaryKeys = [Constants.CITY_ID, Constants.DATETIME]
)
data class HourlyWeatherEntity(
    val feelsLike: Double?,
    val currentTemp: Double?,
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val weatherDescription: String?,
    val weatherIcon: Int?,
    @ColumnInfo(
        name = Constants.DATETIME,
        index = true
    )
    val dateTime: Long,
    @ColumnInfo(
        name = Constants.CITY_ID,
        index = true
    )
    val cityId: Int,
    val uvi: Double?,
    @Embedded(prefix = Constants.AIR_QUALITY)
    val airQuality: AirQuality?,
    val precipitationProbability: Int = 0
)

