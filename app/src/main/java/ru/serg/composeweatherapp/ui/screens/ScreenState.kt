package ru.serg.composeweatherapp.ui.screens

import ru.serg.composeweatherapp.data.dto.UpdatedWeatherItem
import ru.serg.composeweatherapp.data.dto.WeatherItem

sealed interface ScreenState {
    object Empty : ScreenState
    object Loading : ScreenState
    data class Success(val weatherItem: UpdatedWeatherItem) : ScreenState
    data class Error(val message: String? = null) : ScreenState
}