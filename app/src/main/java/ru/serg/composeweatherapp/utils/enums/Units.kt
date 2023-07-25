package ru.serg.composeweatherapp.utils.enums

import androidx.annotation.StringRes
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.utils.Constants

enum class Units(
    val parameterCode: String,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
    @StringRes
    val tempUnits: Int,
    @StringRes
    val windUnits: Int
) {
    METRIC(
        Constants.METRIC,
        R.string.units_metric_title,
        R.string.units_metric_description,
        R.string.units_metric_degrees,
        R.string.units_metric_wind_speed,
    ),
    IMPERIAL(
        Constants.IMPERIAL,
        R.string.units_imperial_title,
        R.string.units_imperial_description,
        R.string.units_imperial_degrees,
        R.string.units_imperial_wind_speed,
    ),
    KELVIN(
        Constants.STANDARD,
        R.string.units_kelvin_title,
        R.string.units_kelvin_description,
        R.string.units_kelvin_degrees,
        R.string.units_kelvin_wind_speed,
    )
}