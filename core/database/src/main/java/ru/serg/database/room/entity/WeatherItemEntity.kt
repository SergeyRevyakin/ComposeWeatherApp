package ru.serg.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import ru.serg.database.Constants
import ru.serg.model.DayWeatherItem
import ru.serg.model.HourWeatherItem

@Entity(
    tableName = Constants.WEATHER_ITEMS,
)
data class WeatherItemEntity(
    val feelsLike: Double?,
    val currentTemp: Double?,
    val windDirection: Int?,
    val windSpeed: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val weatherDescription: String?,
    val weatherIcon: Int?,
    val dateTime: Long?,
    @PrimaryKey
    val cityName: String,
    val lastUpdatedTime: Long,
    val hourlyWeatherList: HourItemList,
    val dailyWeatherList: DailyItemList
) {
    @Serializable
    data class HourItemList(
        val list: List<HourWeatherItem>
    )

    @Serializable
    data class DailyItemList(
        val list: List<DayWeatherItem>
    )
}