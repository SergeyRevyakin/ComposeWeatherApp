package ru.serg.main_pager

object Constants {
    const val BASE_URL_ONECALL = "api.openweathermap.org/data/2.5/onecall"
    const val BASE_URL_WEATHER = "api.openweathermap.org/data/2.5/weather"
    const val BASE_URL_GEOCODING = "api.openweathermap.org/geo/1.0/direct"
    const val WEATHER = "weather"
    const val ONECALL = "onecall"
    const val GEOCODING = "geo"

    const val WEATHER_DATABASE = "weather_database"

    const val CITY_TABLE = "city_table"
    const val WEATHER_ITEMS = "weather_items"
    const val UPDATED_WEATHER_TABLE = "updated_weather_table"
    const val HOUR_WEATHER_TABLE = "hour_weather_table"

    const val CITY_ID = "city_id"
    const val DATETIME = "datetime"

    const val EMPTY_STRING = ""

    val HOUR_FREQUENCY_LIST = listOf(1, 2, 4, 6, 8, 12, 24)

    const val CITY_ITEM = "city_item"

    object Time {
        const val millisecondsToHour: Long = 60L * 60L * 1000L
    }

    object Notifications {
        const val NOTIFICATION_CHANNEL = "Weather"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "Weather notifications"
        const val NOTIFICATION_CHANNEL_ID = "WEATHER_CHANNEL_ID"
        const val NOTIFICATION_CHANNEL_SERVICE_ID = "NOTIFICATION_CHANNEL_SERVICE_ID"
    }

    object DataStore {
        const val IS_DARK_THEME = "IS_DARK_THEME"
        const val FETCH_FREQUENCY = "FETCH_FREQUENCY"
        const val MEASUREMENT_UNITS = "MEASUREMENT_UNITS"
    }

    const val METRIC = "metric"
    const val IMPERIAL = "imperial"
    const val STANDARD = "standard"

    const val ALARM_REQUEST_CODE = 1122
}