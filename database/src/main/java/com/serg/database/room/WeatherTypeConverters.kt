package com.serg.database.room

import androidx.room.TypeConverter
import com.serg.database.room.entity.WeatherItemEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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