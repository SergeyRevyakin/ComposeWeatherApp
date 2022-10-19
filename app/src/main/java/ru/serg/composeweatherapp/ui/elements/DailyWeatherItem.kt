package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.utils.Ext.getDate
import ru.serg.composeweatherapp.utils.Ext.getMinMaxTemp
import ru.serg.composeweatherapp.utils.IconMapper

@Composable
fun DailyWeatherItem(
    item: OneCallResponse.Daily,
    background: Color = Color.White,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(background)
            .padding(12.dp)
            .clickable(onClick = { onClick.invoke() })
            .height(42.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(text = getDate(item.dt), modifier = Modifier.weight(1f))

        Text(
            text = getMinMaxTemp(item.temp),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Image(
            painter = painterResource(id = IconMapper.map(item.weather?.first()?.id ?: 0)),
            contentDescription = "Weather icon",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .weight(1f)
                .graphicsLayer {
                    scaleX = 1.4f
                    scaleY = 1.4f
                }
        )
    }
}