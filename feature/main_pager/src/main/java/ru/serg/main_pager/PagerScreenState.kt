package ru.serg.main_pager

import androidx.compose.runtime.Stable
import ru.serg.model.WeatherItem
import ru.serg.mvi_core.models.IState

@Stable
data class PagerScreenState(
    val isLoading: Boolean,
    val isLocationAvailable: Boolean,
    val isNetworkAvailable: Boolean,
    val weatherList: List<WeatherItem>,
    val activeItem: Int,
    val error: PagerScreenError?,
    val hasWelcomeDialog: Boolean,
) : IState {
    companion object {
        fun defaultState() = PagerScreenState(
            isLoading = true,
            isLocationAvailable = false,
            isNetworkAvailable = true,
            weatherList = emptyList(),
            activeItem = 0,
            error = null,
            hasWelcomeDialog = false,
        )
    }
}
