package ru.serg.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import ru.serg.database.Constants
import ru.serg.model.UpdatedDailyTempItem

@Entity(
    tableName = Constants.UPDATED_WEATHER_TABLE,
    primaryKeys = [Constants.CITY_ID, Constants.DATETIME]
)
data class UpdatedDailyWeatherEntity(
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val weatherDescription: String?,
    val weatherIcon: Int?,
    @ColumnInfo(name = Constants.DATETIME)
    val dateTime: Long,
    val sunrise: Long?,
    val sunset: Long?,
    @ColumnInfo(
        name = Constants.CITY_ID,
        index = true
    )
    val cityId: Int,
    @Embedded(prefix = "daily")
    val dailyWeatherItem: UpdatedDailyTempItem,
    @Embedded(prefix = "feels")
    val feelsLike: UpdatedDailyTempItem,
    val uvi: Double?
)