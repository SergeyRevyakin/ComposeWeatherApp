package ru.serg.composeweatherapp.ui.screens

sealed interface ScreenState {
    object Empty : ScreenState
    object Loading : ScreenState
    data class Success<T>(val data: T) : ScreenState
    data class Error(val message: String? = null) : ScreenState
}