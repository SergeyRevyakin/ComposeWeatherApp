package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.data.data.DayWeatherItem
import ru.serg.composeweatherapp.ui.theme.gradientBorder
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getDate
import ru.serg.composeweatherapp.utils.getMinMaxTemp

@Composable
fun DailyWeatherItem(
    item: DayWeatherItem,
    units: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .gradientBorder(
                borderWidth = 1,
                cornerRadius = 16
            )
            .clickable {
                onClick.invoke()
            }
            .wrapContentHeight()
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable(onClick = { onClick.invoke() })
                .height(42.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(text = getDate(item.dateTime), modifier = Modifier.weight(1f))

            Text(
                text = getMinMaxTemp(item.temp, units),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = item.weatherIcon),
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
}