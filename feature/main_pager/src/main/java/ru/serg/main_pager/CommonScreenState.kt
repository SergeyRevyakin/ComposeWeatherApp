package ru.serg.main_pager

import ru.serg.model.WeatherItem

sealed class CommonScreenState {
    data object Empty : CommonScreenState()

    data object Loading : CommonScreenState()

    data class Success(val weatherList: List<WeatherItem>) : CommonScreenState()

    data class Error(val message: String? = null) : CommonScreenState()
}