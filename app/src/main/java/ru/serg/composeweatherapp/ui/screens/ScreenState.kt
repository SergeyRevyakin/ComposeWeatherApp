package ru.serg.composeweatherapp.ui.screens

import ru.serg.composeweatherapp.data.data.WeatherItem

sealed interface ScreenState {
    object Empty : ScreenState
    object Loading : ScreenState
    data class Success(val weatherItem: WeatherItem) : ScreenState
    data class Error(val message: String? = null) : ScreenState
}