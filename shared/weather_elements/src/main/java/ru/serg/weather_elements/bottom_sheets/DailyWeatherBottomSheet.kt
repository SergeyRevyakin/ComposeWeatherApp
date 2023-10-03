package ru.serg.weather_elements.bottom_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.simple_items.ParamRowItem
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.descriptionSubHeader
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.designsystem.utils.MockItems
import ru.serg.drawables.R.drawable
import ru.serg.model.DailyWeather
import ru.serg.model.enums.Units
import ru.serg.strings.R.string
import ru.serg.weather_elements.getFullDate
import ru.serg.weather_elements.getHour

@Composable
fun DailyWeatherBottomSheet(
    daily: DailyWeather,
    units: Units,
    onDismiss: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.surface
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colors.onBackground),
                RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onDismiss()
            }
    ) {
        Text(
            text = getFullDate(daily.dateTime),
            style = headerStyle,
            modifier = Modifier
                .headerModifier()
        )

        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.height(96.dp)
        ) {

            Text(
                text = daily.weatherDescription,
                style = descriptionSubHeader,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .weight(1f)
            )

            Icon(
                painter = painterResource(id = daily.weatherIcon),
                contentDescription = stringResource(id = string.accessibility_desc_weather_icon),
                modifier = Modifier.size(72.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(id = string.sunrise_value, getHour(daily.sunrise)),
                )

                Text(
                    text = stringResource(id = string.sunset_value, getHour(daily.sunset)),
                )
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(96.dp)
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = drawable.ic_thermometer),
                contentDescription = stringResource(id = string.accessibility_desc_thermometer_icon),
                modifier = Modifier
                    .size(72.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(
                        id = string.morning_value,
                        daily.dailyWeatherItem.morningTemp,
                        stringResource(id = units.tempUnits)
                    )
                )
                Text(
                    text = stringResource(
                        id = string.day_value,
                        daily.dailyWeatherItem.dayTemp,
                        stringResource(id = units.tempUnits)
                    )
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(
                        id = string.evening_value,
                        daily.dailyWeatherItem.eveningTemp,
                        stringResource(id = units.tempUnits)
                    )
                )

                Text(
                    text = stringResource(
                        id = string.night_value,
                        daily.dailyWeatherItem.nightTemp,
                        stringResource(id = units.tempUnits)
                    )
                )
            }
        }
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )


        ParamRowItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            paramIcon = drawable.ic_wind_dir_north,
            paramValue = stringResource(
                id = string.wind_value,
                daily.windSpeed,
                stringResource(id = units.windUnits)
            ),
            rotation = daily.windDirection
        )

        ParamRowItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            paramIcon = drawable.ic_humidity,
            paramValue = stringResource(id = string.humidity_value, daily.humidity),
        )

        ParamRowItem(
            modifier = Modifier
                .fillMaxWidth(),
            paramIcon = drawable.ic_barometer,
            paramValue = stringResource(id = string.pressure_value, daily.pressure),
        )

        Spacer(modifier = Modifier.height(12.dp))
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewDailyWeatherDetailsScreen() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {

        DailyWeatherBottomSheet(
            daily = MockItems.getDailyWeatherMockItem(),
            units = Units.METRIC,
            onDismiss = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLightDailyWeatherDetailsScreen() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {

        DailyWeatherBottomSheet(
            daily = MockItems.getDailyWeatherMockItem(),
            units = Units.METRIC,
            onDismiss = {},
        )
    }
}



