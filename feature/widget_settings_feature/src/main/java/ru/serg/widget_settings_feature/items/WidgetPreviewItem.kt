package ru.serg.widget_settings_feature.items

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.utils.MockItems
import ru.serg.model.CityItem
import ru.serg.model.HourlyWeather
import ru.serg.widgets.getHour
import kotlin.math.roundToInt

@Composable
fun WidgetPreviewItem(
    color: Color,
    modifier: Modifier = Modifier,
    hourWeather: HourlyWeather = MockItems.getHourlyWeatherMockItem(),
    cityItem: CityItem = MockItems.getCityMockItem(),
) {

    val currentColor by animateColorAsState(targetValue = color, label = "")
    val backgroundColor by animateColorAsState(
        targetValue =
        if (android.graphics.Color.luminance(currentColor.toArgb()) > 0.29f) Color.Black else Color.White,
        label = ""
    )

    val bigFont = 38
    val smallFont = 18
    val paddingBottom = 3.dp


    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingBottom),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "02:24",
                    fontSize = bigFont.sp,
                    color = currentColor
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = hourWeather.currentTemp.roundToInt().toString() + "°",
                        style = TextStyle(
                            fontSize = bigFont.sp,
                            fontWeight = FontWeight.Normal,
                            color = currentColor

                        )
                    )

                    Image(
                        painter = painterResource(hourWeather.weatherIcon), contentDescription = "",
                        modifier = Modifier.size(56.dp),
                        colorFilter = ColorFilter.tint(currentColor)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingBottom),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "12, 04 Feb",
                    fontSize = smallFont.sp,
                    color = currentColor
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = hourWeather.weatherDescription,
                        style = TextStyle(
                            color = currentColor,
                            fontSize = smallFont.sp
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cityItem.name,
                    style = TextStyle(
                        color = currentColor,
                        fontSize = smallFont.sp
                    ),
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(
                        text = "Last updated " + getHour(cityItem.lastTimeUpdated),
                        style = TextStyle(
                            color = currentColor,
                            fontSize = 12.sp
                        )
                    )
                    Image(
                        painter = painterResource(ru.serg.drawables.R.drawable.ic_refresh),
                        contentDescription = "",
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(currentColor)
                    )
                }


                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Last recomposition " + getHour(System.currentTimeMillis()),
                        style = TextStyle(
                            color = currentColor,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWidgetDemoWhite() {
    ComposeWeatherAppTheme {
        WidgetPreviewItem(color = Color.White)
    }
}

@Preview
@Composable
private fun PreviewWidgetDemoGreen() {
    ComposeWeatherAppTheme {
        WidgetPreviewItem(color = Color.Green)
    }
}

@Preview
@Composable
private fun PreviewWidgetDemoBlack() {
    ComposeWeatherAppTheme {
        WidgetPreviewItem(color = Color.Black)
    }
}

@Preview
@Composable
private fun PreviewWidgetDemo() {
    ComposeWeatherAppTheme {
        WidgetPreviewItem(color = Color.DarkGray)
    }
}

