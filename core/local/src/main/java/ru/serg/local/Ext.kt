package ru.serg.local

import ru.serg.database.room.entity.CityEntity
import ru.serg.model.CityItem

fun CityItem.toCityEntity() = CityEntity(
    name,
    country,
    latitude,
    longitude,
    isFavorite,
    if (isFavorite) -1 else id,
    lastTimeUpdated = System.currentTimeMillis()
)
