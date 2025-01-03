package ru.serg.weather_elements.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.theme.headerStyle
import ru.serg.model.HourlyWeather
import ru.serg.model.enums.Units
import ru.serg.weather_elements.elements.TodayWeatherCardItem
import ru.serg.weather_elements.getFormattedLastUpdateDate

@Composable
fun HourlyWeatherBottomSheet(
    hourlyWeather: HourlyWeather,
    units: Units,
    modifier: Modifier = Modifier,
    showUvi: () -> Unit,
    showAqi: () -> Unit
) {
    Column {
        Text(
            text = getFormattedLastUpdateDate(hourlyWeather.dateTime),
            style = headerStyle,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .padding(horizontal = 24.dp)
        )

        TodayWeatherCardItem(
            hourlyWeather,
            units,
            modifier,
            hasFrame = false,
            showAqiInfo = showAqi,
            showUviInfo = showUvi
        )
    }
}