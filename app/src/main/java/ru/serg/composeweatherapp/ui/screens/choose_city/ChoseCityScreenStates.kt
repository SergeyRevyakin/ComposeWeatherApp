package ru.serg.composeweatherapp.ui.screens.choose_city

import ru.serg.composeweatherapp.data.dto.CityItem

data class ChoseCityScreenStates(
    val isLoading: Boolean,
    val data: List<CityItem> = emptyList(),
    val message: String? = null
)
