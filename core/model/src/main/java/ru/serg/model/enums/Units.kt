package ru.serg.model.enums

import androidx.annotation.StringRes
import ru.serg.strings.R.string


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
        string.units_metric_title,
        string.units_metric_description,
        string.units_metric_degrees,
        string.units_metric_wind_speed,
    ),
    IMPERIAL(
        Constants.IMPERIAL,
        string.units_imperial_title,
        string.units_imperial_description,
        string.units_imperial_degrees,
        string.units_imperial_wind_speed,
    ),
    KELVIN(
        Constants.STANDARD,
        string.units_kelvin_title,
        string.units_kelvin_description,
        string.units_kelvin_degrees,
        string.units_kelvin_wind_speed,
    )
}