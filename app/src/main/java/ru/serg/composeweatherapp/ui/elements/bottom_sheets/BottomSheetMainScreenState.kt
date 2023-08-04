package ru.serg.composeweatherapp.ui.elements.bottom_sheets

import ru.serg.composeweatherapp.utils.enums.Units
import ru.serg.composeweatherapp.utils.enums.UvIndex
import ru.serg.model.DailyWeather

sealed class BottomSheetMainScreenState {
    data class DailyWeatherScreen(
        val dailyWeather: DailyWeather,
        val units: Units
    ) : BottomSheetMainScreenState()

    data class UviDetailsScreen(val uvIndex: UvIndex) : BottomSheetMainScreenState()
}