package ru.serg.composeweatherapp.ui.elements

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.dto.DayWeatherItem
import ru.serg.composeweatherapp.data.dto.IntraDayTempItem
import ru.serg.composeweatherapp.ui.elements.simple_items.ParamRowItem
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import ru.serg.composeweatherapp.ui.theme.descriptionSubHeader
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getFullDate
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getHour
import ru.serg.composeweatherapp.utils.getTemp

@Composable
fun DailyWeatherBottomSheet(
    daily: DayWeatherItem,
    units: String,
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
                contentDescription = "weather icon",
                modifier = Modifier.size(72.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 16.dp)
            ) {
                Text(
                    text = "Sunrise: ${getHour(daily.sunrise)}",
                )

                Text(
                    text = "Sunrise: ${getHour(daily.sunset)}",
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
                painter = painterResource(id = R.drawable.ic_thermometer),
                contentDescription = "weather icon",
                modifier = Modifier
                    .size(72.dp)
            )


            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = "Morning: ${getTemp(daily.temp.morningTemp, units)}")
                Text(text = "Day: ${getTemp(daily.temp.dayTemp, units)}")
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = "Evening: ${getTemp(daily.temp.eveningTemp, units)}")
                Text(text = "Night ${getTemp(daily.temp.nightTemp, units)}")
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
            paramIcon = R.drawable.ic_wind_dir_north,
            paramValue = "Wind speed: ${daily.windSpeed}m/s",
            rotation = daily.windDirection
        )

        ParamRowItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            paramIcon = R.drawable.ic_humidity,
            paramValue = "Humidity: ${daily.humidity}%",
        )

        ParamRowItem(
            modifier = Modifier
                .fillMaxWidth(),
            paramIcon = R.drawable.ic_barometer,
            paramValue = "Pressure: ${daily.pressure}",
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
        val intra = IntraDayTempItem(
            12.5, 12.5, 12.5, 12.5
        )
        DailyWeatherBottomSheet(
            daily = DayWeatherItem(
                weatherIcon = R.drawable.ic_cloudy,
                feelsLike = intra,
                temp = intra,
                windDirection = 90,
                windSpeed = 5.6,
                humidity = 55,
                pressure = 980,
                weatherDescription = "Cloudy",
                dateTime = getTimeMillis(),
                sunrise = getTimeMillis() + (60L * 60L * 6L * 1000),
                sunset = getTimeMillis() + (60L * 60L * 16L * 1000),
            ),
            units = "℃",
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
        val intra = IntraDayTempItem(
            12.5, 12.5, 12.5, 12.5
        )
        DailyWeatherBottomSheet(
            daily = DayWeatherItem(
                weatherIcon = R.drawable.ic_cloudy,
                feelsLike = intra,
                temp = intra,
                windDirection = 90,
                windSpeed = 5.6,
                humidity = 55,
                pressure = 980,
                weatherDescription = "Cloudy",
                dateTime = getTimeMillis(),
                sunrise = getTimeMillis() + (60L * 60L * 6L * 1000),
                sunset = getTimeMillis() + (60L * 60L * 16L * 1000),
            ),
            units = "℃",
            onDismiss = {},
        )
    }
}


