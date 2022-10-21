package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import ru.serg.composeweatherapp.ui.theme.gradientBorder
import ru.serg.composeweatherapp.utils.Ext
import ru.serg.composeweatherapp.utils.Ext.firstLetterToUpperCase

@Composable
fun TodayWeatherCardItem(
    weatherIcon: Int = R.drawable.ic_rain,
    currentTemp: Int = 12,
    feelsLikeTemp: Int = 10,
    weatherDesc: String = "Rain",
    windDirection: Int = 90,
    windSpeed: Int = 5,
    humidity: Int = 55,
    pressure: Int = 967
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .gradientBorder()
            .wrapContentHeight()
    ) {
        val gradient = Brush.linearGradient(
            listOf(Color.DarkGray, MaterialTheme.colors.background),
        )

        Column(
            modifier = Modifier.background(gradient)
        ) {

            Icon(
                painter = painterResource(
                    id = weatherIcon
                ),
                contentDescription = "Weather icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Text(
                text = weatherDesc.firstLetterToUpperCase(),
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
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
//                            .padding(horizontal = 12.dp)
                            .height(24.dp),
                        paramIcon = R.drawable.ic_thermometer,
                        paramValue = "Temperature: ${Ext.getTemp(temp = currentTemp.toDouble())}",
                    )

                    WeatherParamRowItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
//                            .padding(horizontal = 12.dp)
                            .height(24.dp),
                        paramIcon = R.drawable.ic_thermometer,
                        paramValue = "Feels like: ${Ext.getTemp(feelsLikeTemp.toDouble())}",
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
//                            .padding(horizontal = 12.dp),
                        rotation = windDirection,
                        paramValue = "Wind speed: ${windSpeed}m/s",
                        paramIcon = R.drawable.ic_wind_dir_north
                    )

                    WeatherParamRowItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
//                            .padding(horizontal = 12.dp),
                        paramIcon = R.drawable.ic_humidity,
                        paramValue = "Humidity: $humidity%",
                    )
                }
            }


            WeatherParamRowItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 12.dp),
                paramIcon = R.drawable.ic_barometer,
                paramValue = "Pressure: $pressure",
            )
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun TodayWeatherCardItemPreview() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        TodayWeatherCardItem()
    }
}