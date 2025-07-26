package ru.serg.designsystem.simple_items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.drawables.R.drawable

@Composable
fun ParamRowItem(
    modifier: Modifier = Modifier,
    paramIcon: Int,
    paramValue: String,
    rotation: Int = 0
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
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
                .padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewWeatherParamRowItem() {
    ComposeWeatherAppTheme {
        Scaffold {
            Column(
                Modifier.padding(it)
            ) {
                ParamRowItem(
                    paramIcon = drawable.ic_wind_dir_north,
                    paramValue = "Wind speed: 12m/s",
                    rotation = 0
                )

                Spacer(modifier = Modifier.height(28.dp))

                ParamRowItem(
                    paramIcon = drawable.ic_wind_dir_north,
                    paramValue = "Wind speed: 12m/s\n Direction: North",
                    rotation = 0
                )
            }
        }
    }
}