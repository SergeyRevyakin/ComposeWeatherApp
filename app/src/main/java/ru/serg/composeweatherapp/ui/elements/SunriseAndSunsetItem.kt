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
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.DateUtils

@Composable
fun SunriseSunsetItem(
    sunriseTime: Long,
    sunsetTime: Long,
) {
    Column (
        modifier = Modifier
    ) {

        Text(
            text = "Sunrise",
            style = headerStyle,
            modifier = Modifier
                .headerModifier()
        )

        Row(
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
                Modifier.weight(0.5f), verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_sunrise),
                        contentDescription = "",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(horizontal = 6.dp)

                    )

                    Text(
                        text = DateUtils.getHour(sunriseTime),
                        fontSize = 18.sp,
                        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                    )
                }
            }

            Column(
                Modifier.weight(0.5f),
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
                        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_sunset),
                        contentDescription = "",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(horizontal = 6.dp)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewSunriseSunsetItem() {
    SunriseSunsetItem(sunriseTime = 1646306882000, sunsetTime = 1646347929000)
}