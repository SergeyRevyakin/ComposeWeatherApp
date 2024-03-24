package ru.serg.choose_city_feature.screen.screen_state

import ru.serg.choose_city_feature.Constants.EMPTY_STRING
import ru.serg.model.CityItem

data class ScreenState(
    val isLoading: Boolean,
    val favoriteCitiesList: List<CityItem>,
    val foundCitiesList: List<CityItem>,
    val searchText: String,
    val screenError: ScreenError? = null,
) {
    companion object {
        fun getInitialState() = ScreenState(
            isLoading = false,
            favoriteCitiesList = emptyList(),
            foundCitiesList = emptyList(),
            searchText = EMPTY_STRING,
        )
    }
}
