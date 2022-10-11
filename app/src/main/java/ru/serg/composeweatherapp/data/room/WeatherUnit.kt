package ru.serg.composeweatherapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherUnit(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String
)