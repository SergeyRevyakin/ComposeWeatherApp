package ru.serg.composeweatherapp.ui.elements

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.utils.Ext.getHour
import ru.serg.composeweatherapp.utils.Ext.getTemp

@Composable
fun HourlyWeatherItem(item: OneCallResponse.Hourly) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .width(80.dp)
            .wrapContentHeight()
    ) {
        Column() {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_sun),
                    contentDescription = "Weather icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
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
                    .padding(bottom = 16.dp)
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


