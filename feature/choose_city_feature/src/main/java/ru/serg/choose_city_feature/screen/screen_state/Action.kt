package ru.serg.choose_city_feature.screen.screen_state

import ru.serg.model.CityItem

sealed interface Action {
    data class OnTextChanged(val inputText: String) : Action

    data class OnAddCityClick(val cityItem: CityItem) : Action

    data class OnDeleteCityClick(val cityItem: CityItem) : Action
}