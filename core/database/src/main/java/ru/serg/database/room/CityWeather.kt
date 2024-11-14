package ru.serg.database.room

import androidx.room.Embedded
import androidx.room.Relation
import ru.serg.database.Constants
import ru.serg.database.room.entity.AlertEntity
import ru.serg.database.room.entity.CityEntity
import ru.serg.database.room.entity.DailyWeatherEntity
import ru.serg.database.room.entity.HourlyWeatherEntity

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
    @Relation(
        parentColumn = Constants.CITY_ID,
        entityColumn = Constants.CITY_ID,
    )
    val alertEntityList: List<AlertEntity>
)
