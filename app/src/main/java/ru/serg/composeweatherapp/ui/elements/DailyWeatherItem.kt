package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.utils.Ext.getDate
import ru.serg.composeweatherapp.utils.Ext.getMinMaxTemp

@Composable
fun DailyWeatherItem(
    item: OneCallResponse.Daily,
    background:Color = Color.White
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(background)
            .padding(12.dp)
            .height(42.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(text = getDate(item.dt), color = Color.Black, modifier = Modifier.weight(1f))

        Text(text = getMinMaxTemp(item.temp), color = Color.Black, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))

        Image(painter = painterResource(id = R.drawable.ic_sun),
            contentDescription = "Weather icon", modifier = Modifier.padding(4.dp).weight(1f))
    }
}