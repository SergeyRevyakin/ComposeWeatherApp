package ru.serg.weather_elements.elements

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.utils.MockItems
import ru.serg.designsystem.utils.getTemp
import ru.serg.model.HourlyWeather
import ru.serg.model.enums.Units
import ru.serg.weather_elements.getHourWithNowAndAccent

@Composable
fun HourlyWeatherItem(
    item: HourlyWeather,
    units: Units,
    modifier: Modifier = Modifier,
    offsetSeconds: Long = 0,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(80.dp)
            .wrapContentHeight()
            .background(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colorScheme.onSurface)
            )
            .clickable(onClick = onClick)
    ) {
            Row {
                Image(
                    painter = painterResource(id = item.weatherIcon),
                    contentDescription = "Weather icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(64.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = getHourWithNowAndAccent(
                        item.dateTime,
                        offsetSeconds,
                        MaterialTheme.colorScheme.primary
                    ),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = getTemp(item.currentTemp, stringResource(id = units.tempUnits)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark mode", showBackground = true)
@Composable
fun PreviewDarkHourlyItem() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        Scaffold {
            HourlyWeatherItem(
                item = MockItems.getHourlyWeatherMockItem(),
                units = Units.METRIC,
                modifier = Modifier.padding(it)
            ) {

            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, name = "Light mode", showBackground = true)
@Composable
fun PreviewLightHourlyItem() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        Scaffold {
            HourlyWeatherItem(
                item = MockItems.getHourlyWeatherMockItem(),
                units = Units.METRIC,
                modifier = Modifier.padding(it)
            ) {

            }
        }
    }
}



