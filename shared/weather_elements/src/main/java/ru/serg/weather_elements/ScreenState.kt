package ru.serg.weather_elements

import ru.serg.model.WeatherItem

sealed interface ScreenState {
    object Empty : ScreenState
    object Loading : ScreenState
    data class Success(val weatherItem: WeatherItem) : ScreenState
    data class Error(val message: String? = null) : ScreenState
}