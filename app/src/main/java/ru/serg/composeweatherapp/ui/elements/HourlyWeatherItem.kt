package ru.serg.composeweatherapp.ui.elements

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.data.data.HourWeatherItem
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getHourWithNowAndAccent
import ru.serg.composeweatherapp.utils.Ext.getTemp

@Composable
fun HourlyWeatherItem(item: HourWeatherItem, units: String) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(80.dp)
            .wrapContentHeight()
    ) {
        Column {
            Row {
                Image(
                    painter = painterResource(id = item.weatherIcon),
                    contentDescription = "Weather icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
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
                    text = getHourWithNowAndAccent(item.timestamp, MaterialTheme.colors.primary),
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
                    text = getTemp(item.currentTemp, units),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
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
        HourlyWeatherItem(
            item = HourWeatherItem(
                ru.serg.composeweatherapp.R.drawable.ic_cloudy,
                12.5,
                getTimeMillis()
            ),
            units = "℃"
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark mode", showBackground = true)
@Composable
fun PreviewLightHourlyItem() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        HourlyWeatherItem(
            item = HourWeatherItem(
                ru.serg.composeweatherapp.R.drawable.ic_cloudy,
                12.5,
                getTimeMillis()
            ),
            units = "℃"
        )
    }
}



