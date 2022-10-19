package ru.serg.composeweatherapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LastLocationEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey
    val id: Int = 0
)