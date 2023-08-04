package ru.serg.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithCity(
    @Embedded
    val weatherItemEntity: WeatherItemEntity,
    @Relation(
        parentColumn = "cityName",
        entityColumn = "cityName",
    )
    val cityEntity: CityEntity?
)