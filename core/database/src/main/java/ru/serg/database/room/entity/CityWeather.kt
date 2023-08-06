package ru.serg.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation
import ru.serg.database.Constants

data class CityWeather(

    @Relation(
        parentColumn = Constants.CITY_ID,
        entityColumn = Constants.CITY_ID,
    )
    val hourlyWeatherEntity: List<HourlyWeatherEntity>,
    @Embedded
    val cityEntity: CityEntity,
    @Relation(
        parentColumn = Constants.CITY_ID,
        entityColumn = Constants.CITY_ID,
    )
    val dailyWeatherEntity: List<DailyWeatherEntity>,

    )
