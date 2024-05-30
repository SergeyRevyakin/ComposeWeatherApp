package ru.serg.choose_city_feature.screen

import ru.serg.model.CityItem

sealed interface Intent {

    data class OnTextChanges(val inputText: String) : Intent

    data object OnNetworkError : Intent

    data class OnCityDataUpdated(val list: List<CityItem>) : Intent

    data class FavouriteCityListChanged(val list: List<CityItem>) : Intent

    data class OnLoading(val isLoading: Boolean) : Intent

}
