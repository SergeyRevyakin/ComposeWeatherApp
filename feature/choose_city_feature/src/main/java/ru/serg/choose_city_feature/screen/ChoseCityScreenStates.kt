package ru.serg.choose_city_feature.screen

import ru.serg.model.CityItem

data class ChoseCityScreenStates(
    val isLoading: Boolean,
    val data: List<CityItem> = emptyList(),
    val message: String? = null
)

sealed class ScreenState(
    val isLoading: Boolean,
    val favoriteCitiesList: List<CityItem>,
    val foundCitiesList: List<CityItem>,
    val searchText: String
) {

}