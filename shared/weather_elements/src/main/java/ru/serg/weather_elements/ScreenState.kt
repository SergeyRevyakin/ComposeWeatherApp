package ru.serg.weather_elements

import ru.serg.model.WeatherItem

sealed interface ScreenState {
    data object Loading : ScreenState
    data class Success(val weatherItem: WeatherItem) : ScreenState
    data class Error(val message: String? = null, val throwable: Throwable? = null) : ScreenState
}