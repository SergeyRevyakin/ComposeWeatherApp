package ru.serg.composeweatherapp.utils.enums

import androidx.compose.runtime.Immutable
import ru.serg.composeweatherapp.R
@Immutable
enum class UvIndex(
    val descriptionId: Int,
    val detailsId: Int
) {
    LOW(R.string.uvi_low, R.string.uiv_low_description),
    MODERATE(R.string.uvi_modetate, R.string.uiv_moderate_description),
    HIGH(R.string.uvi_high, R.string.uiv_high_description),
    VERY_HIGH(R.string.uvi_very_high, R.string.uiv_very_high_description),
    EXTREME(R.string.uvi_extreme, R.string.uiv_extreme_description),
}