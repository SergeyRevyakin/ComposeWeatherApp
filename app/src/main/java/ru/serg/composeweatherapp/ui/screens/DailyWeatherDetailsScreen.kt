package ru.serg.composeweatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.ktor.util.date.*
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.data.DayWeatherItem
import ru.serg.composeweatherapp.data.data.IntraDayTempItem
import ru.serg.composeweatherapp.ui.elements.WeatherParamRowItem
import ru.serg.composeweatherapp.ui.theme.*
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getFullDate
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getHour
import ru.serg.composeweatherapp.utils.Ext

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DailyWeatherDetailsScreen(
    daily: DayWeatherItem,
    modifier: Modifier,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        )
    ) {

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(24.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .gradientBorder(2, 24)
        ) {
            Column {
                Text(
                    text = getFullDate(daily.dateTime),
                    style = MaterialTheme.typography.headerStyle,
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
                        text = daily.weatherDescription.orEmpty(),
                        style = MaterialTheme.typography.descriptionSubHeader,
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
                        Text(text = "Morning: ${Ext.getTemp(daily.temp?.morningTemp)}")
                        Text(text = "Day: ${Ext.getTemp(daily.temp?.dayTemp)}")
                    }

                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(text = "Evening: ${Ext.getTemp(daily.temp?.eveningTemp)}")
                        Text(text = "Night ${Ext.getTemp(daily.temp?.nightTemp)}")
                    }
                }
                Divider(
                    color = MaterialTheme.colors.primary,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )


                WeatherParamRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    paramIcon = R.drawable.ic_wind_dir_north,
                    paramValue = "Wind speed: ${daily.windSpeed}m/s",
                    rotation = daily.windDirection ?: 0
                )

                WeatherParamRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    paramIcon = R.drawable.ic_humidity,
                    paramValue = "Humidity: ${daily.humidity}%",
                )

                WeatherParamRowItem(
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .padding(12.dp),
                    paramIcon = R.drawable.ic_barometer,
                    paramValue = "Pressure: ${daily.pressure}",
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

        }
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
        DailyWeatherDetailsScreen(
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
            onDismiss = {},
            modifier = Modifier
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
        DailyWeatherDetailsScreen(
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
            onDismiss = {},
            modifier = Modifier
        )
    }
}


