package ru.serg.common

enum class UvIndex(
    val descriptionId: Int,
    val detailsId: Int
) {
    LOW(R.string.uvi_low, R.string.uiv_low_description),
    MODERATE(R.string.uvi_moderate, R.string.uiv_moderate_description),
    HIGH(R.string.uvi_high, R.string.uiv_high_description),
    VERY_HIGH(R.string.uvi_very_high, R.string.uiv_very_high_description),
    EXTREME(R.string.uvi_extreme, R.string.uiv_extreme_description),
}

fun mapUvIndex(value: Double): UvIndex =
    when (value.toInt()) {
        in 0..2 -> UvIndex.LOW
        in 3..5 -> UvIndex.MODERATE
        in 6..7 -> UvIndex.HIGH
        in 8..10 -> UvIndex.VERY_HIGH
        else -> UvIndex.EXTREME
    }