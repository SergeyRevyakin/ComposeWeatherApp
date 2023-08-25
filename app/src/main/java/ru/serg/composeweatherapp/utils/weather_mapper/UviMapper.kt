package ru.serg.composeweatherapp.utils.weather_mapper

import ru.serg.common.UvIndex

object UviMapper {
    fun map(value: Double): UvIndex =
        when(value.toInt()) {
            in 0..2 -> UvIndex.LOW
            in 3..5  -> UvIndex.MODERATE
            in 6..7  -> UvIndex.HIGH
            in 8..10  -> UvIndex.VERY_HIGH
            else -> UvIndex.EXTREME
        }
}