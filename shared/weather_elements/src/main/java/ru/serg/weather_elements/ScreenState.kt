package ru.serg.weather_elements

import ru.serg.model.UpdatedWeatherItem

sealed interface ScreenState {
    object Empty : ScreenState
    object Loading : ScreenState
    data class Success(val weatherItem: UpdatedWeatherItem) : ScreenState
    data class Error(val message: String? = null) : ScreenState
}