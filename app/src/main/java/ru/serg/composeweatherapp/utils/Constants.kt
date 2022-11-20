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
}