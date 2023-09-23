package ru.serg.designsystem.simple_items

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.drawables.R.drawable
import ru.serg.strings.R.string

@Composable
fun WeatherParamItem(
    param: String,
    paramIcon: Int,
    paramName: String,
    modifier: Modifier = Modifier,
    rotation: Int = 0,
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)

    ) {
        Row {
            Icon(
                painter = painterResource(id = paramIcon),
                contentDescription = stringResource(id = string.accessibility_desc_weather_icon),
                modifier = Modifier
                    .size(42.dp)
                    .rotate(rotation.toFloat())
            )
        }
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {

            Text(
                text = param,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
        Row(
            modifier = Modifier
        ) {
            Text(
                text = paramName,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark mode", showBackground = true)
@Composable
fun PreviewDarkWeatherParamItem() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        WeatherParamItem(
            paramName = "wind",
            param = "4.2 m/s",
            paramIcon = drawable.ic_wind_dir_north
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark mode", showBackground = true)
@Composable
fun PreviewWeatherParamItem() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        WeatherParamItem(
            paramName = "wind",
            param = "4.2 m/s",
            paramIcon = drawable.ic_wind_dir_north
        )
    }
}



