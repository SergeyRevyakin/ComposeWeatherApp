package ru.serg.weather_elements.bottom_sheets

import androidx.compose.runtime.Composable

@Composable
fun MainScreenBottomSheet(
    screenState: BottomSheetMainScreenState,
    onDismiss: () -> Unit
) {
    when (screenState) {
        is BottomSheetMainScreenState.UviDetailsScreen -> UviBottomSheet(
            uvIndex = screenState.uvIndex,
            onDismiss = onDismiss
        )

        is BottomSheetMainScreenState.DailyWeatherScreen -> DailyWeatherBottomSheet(
            daily = screenState.dailyWeather,
            units = screenState.units,
            onDismiss = onDismiss
        )
    }

}