package ru.serg.composeweatherapp.data.room.entity

import androidx.room.Embedded
import androidx.room.Relation
import ru.serg.composeweatherapp.utils.Constants

data class UpdatedCityWeather(

    @Relation(
        parentColumn = Constants.CITY_ID,
        entityColumn = Constants.CITY_ID,
    )
    val hourlyWeatherEntity: List<UpdatedHourlyWeatherEntity>,
    @Embedded
    val cityEntity: CityEntity,
    @Relation(
        parentColumn = Constants.CITY_ID,
        entityColumn = Constants.CITY_ID,
    )
    val updatedDailyWeatherEntity: List<UpdatedDailyWeatherEntity>,

    )