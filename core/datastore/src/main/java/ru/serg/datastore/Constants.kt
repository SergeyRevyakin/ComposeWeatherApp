package ru.serg.datastore

object Constants {


    val HOUR_FREQUENCY_LIST = listOf(1, 2, 4, 6, 8, 12, 24)

    object DataStore {
        const val IS_DARK_THEME = "IS_DARK_THEME"
        const val FETCH_FREQUENCY = "FETCH_FREQUENCY"
        const val MEASUREMENT_UNITS = "MEASUREMENT_UNITS"
        const val IS_USER_NOTIFICATIONS_ON = "IS_USER_NOTIFICATIONS_ON"

        //Widget settings
        const val WIDGET_COLOR_CODE = "WIDGET_COLOR_CODE"
    }

    const val WHITE_COLOR_CODE = -4294967296
}