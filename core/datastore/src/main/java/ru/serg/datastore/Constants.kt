package ru.serg.datastore

object Constants {


    val HOUR_FREQUENCY_LIST = listOf(1, 2, 4, 6, 8, 12, 24)

    object AppSettings {
        const val IS_DARK_THEME = "IS_DARK_THEME"
        const val FETCH_FREQUENCY = "FETCH_FREQUENCY"
        const val MEASUREMENT_UNITS = "MEASUREMENT_UNITS"
        const val IS_USER_NOTIFICATIONS_ON = "IS_USER_NOTIFICATIONS_ON"
    }

    object WidgetSettings {
        const val WIDGET_COLOR_CODE = "WIDGET_COLOR_CODE"
        const val WIDGET_SETTINGS_BIG_FONT = "WIDGET_SETTINGS_BIG_FONT"
        const val WIDGET_SETTINGS_SMALL_FONT = "WIDGET_SETTINGS_SMALL_FONT"
        const val WIDGET_SETTINGS_BOTTOM_PADDING = "WIDGET_SETTINGS_BOTTOM_PADDING"
        const val IS_WIDGET_SYSTEM_DATA_SHOWN = "IS_WIDGET_SYSTEM_DATA_SHOWN"
        const val WIDGET_SETTINGS_ICON_SIZE = "WIDGET_SETTINGS_ICON_SIZE"

        const val WHITE_COLOR_CODE = -4294967296
        const val DEFAULT_BIG_FONT_SIZE = 38
        const val DEFAULT_SMALL_FONT_SIZE = 18
        const val DEFAULT_BOTTOM_PADDING = 3
        const val DEFAULT_IS_WIDGET_SYSTEM_DATA_SHOWN = false
        const val DEFAULT_WIDGET_ICON_SIZE = 56
    }
}


