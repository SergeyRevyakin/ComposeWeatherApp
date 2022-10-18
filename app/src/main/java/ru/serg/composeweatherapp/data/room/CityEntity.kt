package ru.serg.composeweatherapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey()
    val name: String,
    val country: String?,
    val latitude: Double?,
    val longitude: Double?,

)