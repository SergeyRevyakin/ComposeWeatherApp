package ru.serg.composeweatherapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.serg.composeweatherapp.utils.Constants

@Entity(
    tableName = Constants.SEARCH_HISTORY
)
data class CityEntity(
    val cityName: String,
    val country: String?,
    val latitude: Double?,
    val longitude: Double?,
    val isFavorite: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.CITY_ID)
    val id: Int = 0,
    var lastTimeUpdated: Long?
)