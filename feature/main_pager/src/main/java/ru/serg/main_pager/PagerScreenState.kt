package ru.serg.main_pager

import androidx.compose.runtime.Stable
import ru.serg.model.WeatherItem

@Stable
data class PagerScreenState(
    val isLoading: Boolean,
    val isLocationAvailable: Boolean,
    val isNetworkAvailable: Boolean,
    val weatherList: List<WeatherItem>,
    val activeItem: Int,
    val error: PagerScreenError?,
    val isStartUp: Boolean,
    val hasWelcomeDialog: Boolean
) {
    companion object {
        fun defaultState() = PagerScreenState(
            isLoading = false,
            isLocationAvailable = false,
            isNetworkAvailable = true,
            weatherList = emptyList(),
            activeItem = 0,
            error = null,
            isStartUp = true,
            hasWelcomeDialog = false
        )
    }
}
