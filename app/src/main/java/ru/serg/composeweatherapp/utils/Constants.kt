package ru.serg.composeweatherapp.utils

object Constants {
    const val BASE_URL_ONECALL = "api.openweathermap.org/data/2.5/onecall"
    const val BASE_URL_WEATHER = "api.openweathermap.org/data/2.5/weather"
    const val BASE_URL_GEOCODING = "api.openweathermap.org/geo/1.0/direct"
    const val WEATHER = "weather"
    const val ONECALL = "onecall"
    const val GEOCODING = "geo"

    const val WEATHER_DATABASE = "weather_database"

    const val LAST_LOCATION = "LASTLOCATIONENTITY"

    const val SEARCH_HISTORY = "city_search_history"
    const val WEATHER_ITEMS = "weather_items"

    const val EMPTY_STRING = ""

    val HOUR_FREQUENCY_LIST = listOf(1, 2, 4, 6, 8, 12, 24)

    const val CITY_ITEM = "city_item"

    object Notifications {
        const val NOTIFICATION_CHANNEL = "weather_channel"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "Weather notifications"
        const val NOTIFICATION_CHANNEL_ID = "WEATHER_CHANNEL_ID"
    }

    object DataStore {
        const val IS_DARK_THEME = "IS_DARK_THEME"
        const val IS_BACKGROUND_FETCH_ENABLED = "IS_BACKGROUND_FETCH_ENABLED"
        const val FETCH_FREQUENCY = "FETCH_FREQUENCY"
        const val MEASUREMENT_UNITS = "MEASUREMENT_UNITS"

        enum class Units(val parameterCode: String, val title: String, val description: String, val tempUnits: String, val windUnits: String){
            METRIC("metric", "Metric", "Temperature in Celsius, wind speed in meter/sec", "℃", "m/s"),
            IMPERIAL( "imperial", "Imperial", "Temperature in Fahrenheit and wind speed in miles/hour", "℉", "mph"),
            KELVIN("standard", "Kelvin", "Temperature in Kelvin and wind speed in meter/sec", "K", "m/s")
        }
    }

    const val ALARM_REQUEST_CODE = 1122
}