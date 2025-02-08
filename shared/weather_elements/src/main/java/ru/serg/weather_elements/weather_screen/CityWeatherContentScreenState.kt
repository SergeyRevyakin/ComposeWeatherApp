package ru.serg.weather_elements.weather_screen

import androidx.compose.runtime.Immutable
import ru.serg.model.enums.Units

@Immutable
data class CityWeatherContentScreenState(
    val units: Units,
    val isAlertsEnabled: Boolean
)