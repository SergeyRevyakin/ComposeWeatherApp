package ru.serg.composeweatherapp.ui.elements.bottom_sheets

import ru.serg.common.UvIndex
import ru.serg.model.DailyWeather
import ru.serg.model.enums.Units

sealed class BottomSheetMainScreenState {
    data class DailyWeatherScreen(
        val dailyWeather: DailyWeather,
        val units: Units
    ) : BottomSheetMainScreenState()

    data class UviDetailsScreen(val uvIndex: UvIndex) : BottomSheetMainScreenState()
}