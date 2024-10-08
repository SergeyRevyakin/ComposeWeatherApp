package ru.serg.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import ru.serg.database.Constants
import ru.serg.model.DailyTempItem

@Entity(
    tableName = Constants.DAY_WEATHER_TABLE,
    primaryKeys = [Constants.CITY_ID, Constants.DATETIME]
)
data class DailyWeatherEntity(
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
    @Embedded(prefix = Constants.DAILY)
    val dailyWeatherItem: DailyTempItem,
    @Embedded(prefix = Constants.FEELS)
    val feelsLike: DailyTempItem,
    val uvi: Double?,
    val precipitationProbability: Int = 0
)