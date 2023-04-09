package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.data.dto.DayWeatherItem
import ru.serg.composeweatherapp.ui.theme.gradientBorder
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getDate
import ru.serg.composeweatherapp.utils.getMinMaxTemp

@Composable
fun DailyWeatherItem(
    item: DayWeatherItem,
    units: String,
    onClick: () -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                spotColor = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .gradientBorder(
                borderWidth = 2,
                cornerRadius = 24
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick.invoke()
            }
            .wrapContentHeight()
            .background(
                MaterialTheme.colors.surface
                    .copy(alpha = 0.9f)
                    .compositeOver(Color.White)
            )
            .padding(16.dp)
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            text = getDate(item.dateTime),
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = getMinMaxTemp(item.temp, units),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
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