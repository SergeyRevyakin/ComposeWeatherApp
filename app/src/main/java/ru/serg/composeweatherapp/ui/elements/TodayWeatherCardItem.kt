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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import ru.serg.composeweatherapp.ui.theme.gradientBorder
import ru.serg.composeweatherapp.utils.Ext

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
                text = weatherDesc,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .height(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_thermometer),
                            contentDescription = "Temperature",
                            modifier = Modifier
                                .height(24.dp)
                        )
                        Text(
                            text = "Temperature: ${Ext.getTemp(temp = currentTemp.toDouble())}",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 12.dp)
                            .height(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_thermometer),
                            contentDescription = "Feels like",
                            modifier = Modifier
                                .height(24.dp)
                        )
                        Text(
                            text = "Feels like: ${Ext.getTemp(feelsLikeTemp.toDouble())}",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .padding(top = 16.dp)
                            .padding(horizontal = 12.dp)
//                            .height(30.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_wind_dir_north),
                            contentDescription = "Direction",
                            modifier = Modifier
                                .rotate(windDirection.toFloat())
                                .height(24.dp)
                        )

                        Text(
                            text = "Wind speed: ${windSpeed}m/s",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 12.dp)

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = "Humidity",
                            modifier = Modifier
                                .height(24.dp)
                        )

                        Text(
                            text = "Humidity: $humidity%",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_barometer),
                    contentDescription = "Pressure",
                    modifier = Modifier
                        .height(24.dp)
                )

                Text(
                    text = "Pressure: $pressure",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .height(24.dp)
                )
            }
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