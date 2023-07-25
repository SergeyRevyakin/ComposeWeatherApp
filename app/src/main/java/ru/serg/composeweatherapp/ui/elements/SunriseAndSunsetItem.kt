package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.DateUtils

@Composable
fun SunriseSunsetItem(
    sunriseTime: Long,
    sunsetTime: Long,
) {
    Column(
        modifier = Modifier
    ) {

        Text(
            text = "Sunrise",
            style = headerStyle,
            modifier = Modifier
                .headerModifier()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
                .shadow(
                    elevation = 10.dp,
                    spotColor = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(24.dp)
                )
                .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(24.dp))
                .background(
                    MaterialTheme.colors.surface
                        .copy(alpha = 0.9f)
                        .compositeOver(Color.White)
                )
                .padding(16.dp)

        ) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 6.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_sunrise),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically)

                    )

                    Text(
                        text = DateUtils.getHour(sunriseTime),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                            .padding(start = 12.dp),
                    )
                }
            }

            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 6.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        text = DateUtils.getHour(sunsetTime),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                            .padding(end = 12.dp),
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_sunset),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewSunriseSunsetItem() {
    ComposeWeatherAppTheme {
        SunriseSunsetItem(sunriseTime = 1646306882000, sunsetTime = 1646347929000)
    }
}

@Preview
@Composable
fun PreviewSunriseSunsetDarkItem() {
    val isDark = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(darkTheme = isDark) {
        SunriseSunsetItem(sunriseTime = 1646306882000, sunsetTime = 1646347929000)
    }
}