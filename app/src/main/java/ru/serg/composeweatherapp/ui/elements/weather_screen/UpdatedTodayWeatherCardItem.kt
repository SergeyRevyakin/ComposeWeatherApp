package ru.serg.composeweatherapp.ui.elements.weather_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.dto.HourlyWeather
import ru.serg.composeweatherapp.ui.elements.WeatherParamRowItem
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import ru.serg.composeweatherapp.ui.theme.gradientBorder
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getFormattedLastUpdateDate
import ru.serg.composeweatherapp.utils.MockItems
import ru.serg.composeweatherapp.utils.firstLetterToUpperCase
import ru.serg.composeweatherapp.utils.getTemp

@Composable
fun UpdatedTodayWeatherCardItem(
    weatherItem: HourlyWeather,
    units: String,
    lastUpdatedTime: Long = getTimeMillis()
) {
    val gradient = Brush.linearGradient(
        listOf(
            MaterialTheme.colors.background.copy(alpha = 0.8f)
                .compositeOver(MaterialTheme.colors.onBackground),
            MaterialTheme.colors.background
        ),
    )

    Column(
        modifier = Modifier
            .padding(12.dp)
            .shadow(
                elevation = 10.dp,
                spotColor = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .gradientBorder()
            .background(gradient)
            .fillMaxWidth()
            .wrapContentHeight()

    ) {

        Icon(
            painter = painterResource(
                id = weatherItem.weatherIcon
            ),
            contentDescription = "Weather icon",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Text(
            text = weatherItem.weatherDescription.firstLetterToUpperCase(),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .padding(horizontal = 24.dp),
            style = TextStyle(
                letterSpacing = 1.5.sp
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                WeatherParamRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    paramIcon = R.drawable.ic_thermometer,
                    paramValue = "Temperature: ${
                        getTemp(
                            temp = weatherItem.currentTemp,
                            units
                        )
                    }",
                )

                WeatherParamRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(24.dp),
                    paramIcon = R.drawable.ic_thermometer,
                    paramValue = "Feels like: ${getTemp(temp = weatherItem.feelsLike, units)}",
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                WeatherParamRowItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    rotation = weatherItem.windDirection,
                    paramValue = "Wind speed: ${weatherItem.windSpeed}m/s",
                    paramIcon = R.drawable.ic_wind_dir_north
                )

                WeatherParamRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    paramIcon = R.drawable.ic_humidity,
                    paramValue = "Humidity: ${weatherItem.humidity}%",
                )
            }
        }

        WeatherParamRowItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(bottom = 16.dp)
                .padding(horizontal = 12.dp),
            paramIcon = R.drawable.ic_barometer,
            paramValue = "Pressure: ${weatherItem.pressure}",
        )

        Text(
            text = "Last updated: ${getFormattedLastUpdateDate(lastUpdatedTime)}",
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TodayWeatherCardItemPreview() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        UpdatedTodayWeatherCardItem(
            MockItems.getHourlyWeatherMockItem(),
            "℃"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodayWeatherCardItemPreviewDark() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        UpdatedTodayWeatherCardItem(
            MockItems.getHourlyWeatherMockItem(),
            "℃"
        )
    }
}