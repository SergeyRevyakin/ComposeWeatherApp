package ru.serg.common

import ru.serg.strings.R.string

enum class UvIndex(
    val descriptionId: Int,
    val detailsId: Int
) {
    LOW(string.uvi_low, string.uiv_low_description),
    MODERATE(string.uvi_moderate, string.uiv_moderate_description),
    HIGH(string.uvi_high, string.uiv_high_description),
    VERY_HIGH(string.uvi_very_high, string.uiv_very_high_description),
    EXTREME(string.uvi_extreme, string.uiv_extreme_description),
}

fun mapUvIndex(value: Double): UvIndex =
    when (value.toInt()) {
        in 0..2 -> UvIndex.LOW
        in 3..5 -> UvIndex.MODERATE
        in 6..7 -> UvIndex.HIGH
        in 8..10 -> UvIndex.VERY_HIGH
        else -> UvIndex.EXTREME
    }