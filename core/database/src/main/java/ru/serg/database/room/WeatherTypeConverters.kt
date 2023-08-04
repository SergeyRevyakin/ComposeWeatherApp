package ru.serg.database.room

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.serg.database.room.entity.WeatherItemEntity

class WeatherTypeConverters {
    @TypeConverter
    fun fromDayWeatherItemToJSON(items: WeatherItemEntity.DailyItemList): String {
        return Json.encodeToString(items)
    }

    @TypeConverter
    fun fromJSONToDayWeatherItem(json: String): WeatherItemEntity.DailyItemList {
        return Json.decodeFromString(json)
    }

    @TypeConverter
    fun fromHourWeatherItemToJSON(items: WeatherItemEntity.HourItemList): String {
        return Json.encodeToString(items)
    }

    @TypeConverter
    fun fromJSONToHourWeatherItem(json: String): WeatherItemEntity.HourItemList {
        return Json.decodeFromString(json)
    }
}