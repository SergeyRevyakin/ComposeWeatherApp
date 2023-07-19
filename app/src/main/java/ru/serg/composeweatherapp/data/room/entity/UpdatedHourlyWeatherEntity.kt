package ru.serg.composeweatherapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.serg.composeweatherapp.utils.Constants

@Entity(
    tableName = Constants.HOUR_WEATHER_TABLE,
    primaryKeys = [Constants.CITY_ID, Constants.DATETIME]
)
data class UpdatedHourlyWeatherEntity(
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
)