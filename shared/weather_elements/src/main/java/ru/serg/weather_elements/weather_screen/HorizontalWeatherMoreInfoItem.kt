package ru.serg.weather_elements.weather_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.simple_items.WeatherParamItem
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.utils.MockItems
import ru.serg.drawables.R.drawable
import ru.serg.model.HourlyWeather
import ru.serg.model.enums.Units
import ru.serg.strings.R.string

@Composable
fun HorizontalWeatherMoreInfoItem(item: HourlyWeather, units: Units) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        WeatherParamItem(
            paramName = stringResource(id = string.wind),
            param = "${item.windSpeed} ${stringResource(id = units.windUnits)}",
            paramIcon = drawable.ic_wind_dir_north,
            rotation = item.windDirection
        )

        WeatherParamItem(
            paramName = stringResource(id = string.humidity),
            param = "${item.humidity} %",
            paramIcon = drawable.ic_humidity
        )
        
        WeatherParamItem(
            paramName = stringResource(id = string.pressure),
            param = "${item.pressure}",
            paramIcon = drawable.ic_barometer
        )
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    name = "Dark mode",
    showBackground = true
)
@Composable
fun HorizontalWeatherMoreInfoItemPreview() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        HorizontalWeatherMoreInfoItem(
            MockItems.getHourlyWeatherMockItem(),
            Units.METRIC
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun HorizontalWeatherMoreInfoItemPreviewDark() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        HorizontalWeatherMoreInfoItem(
            MockItems.getHourlyWeatherMockItem(),
            Units.METRIC
        )
    }
}