package ru.serg.composeweatherapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import ru.serg.composeweatherapp.data.data.DayWeatherItem
import ru.serg.composeweatherapp.data.data.HourWeatherItem

@Entity
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