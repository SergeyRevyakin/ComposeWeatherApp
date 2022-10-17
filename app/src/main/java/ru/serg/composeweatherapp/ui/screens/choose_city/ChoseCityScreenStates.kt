package ru.serg.composeweatherapp.ui.screens.choose_city

import ru.serg.composeweatherapp.data.data.CityItem
//
//sealed class ChoseCityScreenStates(
//    val isLoading:Boolean,
//    val data: List<CityItem>?=null,
//    val message: String?=null
//){
//    class Loading(
//        isLoading: Boolean = true
//    ) : ChoseCityScreenStates(isLoading)
//
//    class Content(
//        isLoading: Boolean = false,
//        data: List<CityItem>
//    ) : ChoseCityScreenStates(isLoading, data)
//
//    class EmptyInput(
//        isLoading: Boolean = false,
//    ) : ChoseCityScreenStates(isLoading)
//
//    class Error(
//        isLoading: Boolean = false,
//        message: String
//    ) : ChoseCityScreenStates(isLoading, message = message)
//}

data class ChoseCityScreenStates(
    val isLoading:Boolean,
    val data: List<CityItem> = emptyList(),
    val message: String?=null
)
