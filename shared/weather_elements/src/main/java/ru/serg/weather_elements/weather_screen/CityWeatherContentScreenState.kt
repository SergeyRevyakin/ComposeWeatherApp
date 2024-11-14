package ru.serg.weather_elements.weather_screen

import ru.serg.model.enums.Units

data class CityWeatherContentScreenState(
    val units: Units,
    val isAlertsEnabled: Boolean
)