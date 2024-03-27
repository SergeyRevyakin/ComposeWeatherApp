package ru.serg.designsystem.simple_items

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.utils.AnimWeather
import ru.serg.designsystem.utils.firstLetterToUpperCase
import ru.serg.designsystem.utils.getTemp
import ru.serg.model.enums.Units

@Composable
fun CurrentTempItem(
    temp: Int,
    description: String,
    @StringRes
    units: Int = Units.METRIC.tempUnits
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .padding(horizontal = 12.dp),
    ) {
        AnimWeather(targetState = temp) {
            Text(
                text = getTemp(
                    temp = it,
                    stringResource(id = units)
                ) + ", ",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    letterSpacing = 2.sp
                )
            )
        }

        AnimWeather(targetState = description) {
            Text(
                text = it.firstLetterToUpperCase(),
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,

                style = TextStyle(
                    letterSpacing = 2.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewCurrentTempItem() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        CurrentTempItem(temp = 12, description = "Scattered Clouds")
    }
}