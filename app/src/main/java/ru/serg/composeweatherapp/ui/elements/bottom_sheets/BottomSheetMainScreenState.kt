package ru.serg.composeweatherapp.ui.elements.bottom_sheets

import ru.serg.composeweatherapp.data.dto.DailyWeather
import ru.serg.composeweatherapp.utils.enums.Units
import ru.serg.composeweatherapp.utils.enums.UvIndex

sealed class BottomSheetMainScreenState {
    data class DailyWeatherScreen(
        val dailyWeather: DailyWeather,
        val units: Units
    ) : BottomSheetMainScreenState()

    data class UviDetailsScreen(val uvIndex: UvIndex) : BottomSheetMainScreenState()
}