package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.R

@Composable
fun WeatherParamRowItem(
    modifier: Modifier = Modifier,
    paramIcon: Int = R.drawable.ic_wind_dir_north,
    paramValue: String = "Wind speed: 12m/s",
    rotation: Int = 0
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = paramIcon),
            contentDescription = "Direction",
            modifier = Modifier
                .rotate(rotation.toFloat())
                .height(24.dp)
        )

        Text(
            text = paramValue,
            fontSize = 16.sp,
            modifier = Modifier
//                .fillMaxWidth()
                .height(24.dp)
        )
    }
}