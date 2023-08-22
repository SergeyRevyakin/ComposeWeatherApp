package ru.serg.composeweatherapp.ui.elements.weather_screen

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
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.utils.weather_mapper.MockItems
import ru.serg.designsystem.simple_items.WeatherParamItem
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.model.HourlyWeather
import ru.serg.model.enums.Units

@Composable
fun HorizontalWeatherMoreInfoItem(item: HourlyWeather, units: Units) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        WeatherParamItem(
            paramName = stringResource(id = R.string.wind),
            param = "${item.windSpeed} ${stringResource(id = units.windUnits)}",
            paramIcon = R.drawable.ic_wind_dir_north,
            rotation = item.windDirection
        )

        WeatherParamItem(
            paramName = stringResource(id = R.string.humidity),
            param = "${item.humidity} %",
            paramIcon = R.drawable.ic_humidity
        )
        
        WeatherParamItem(
            paramName = stringResource(id = R.string.pressure),
            param = "${item.pressure}",
            paramIcon = R.drawable.ic_barometer
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