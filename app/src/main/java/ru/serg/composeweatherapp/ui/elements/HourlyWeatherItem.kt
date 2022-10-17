package ru.serg.composeweatherapp.ui.elements

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.utils.Ext.getHour
import ru.serg.composeweatherapp.utils.Ext.getTemp
import ru.serg.composeweatherapp.utils.IconMapper

@Composable
fun HourlyWeatherItem(item: OneCallResponse.Hourly) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .width(80.dp)
            .wrapContentHeight()
    ) {
        Column {
            Row {
                Image(
                    painter = painterResource(id = IconMapper.map(item.weather?.first()?.id ?: 0)),
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
                    text = getHour(item.dt),
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
                    text = getTemp(item.feelsLike),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(name = "Light mode")
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark mode", showBackground = true)
@Composable
fun PreviewHourlyItem() {
    HourlyWeatherItem(item = OneCallResponse.Hourly.getTestExample())
}


