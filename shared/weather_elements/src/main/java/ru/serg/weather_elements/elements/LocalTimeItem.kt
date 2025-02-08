package ru.serg.weather_elements.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.serg.designsystem.theme.descriptionSubHeader
import ru.serg.strings.R.string
import ru.serg.weather_elements.getFormattedTime

@Composable
fun LocalTimeItem(offsetSeconds: Long, modifier: Modifier = Modifier) {
    var currentTime by remember {
        mutableStateOf(
            getFormattedTime(
                System.currentTimeMillis(),
                offsetSeconds
            )
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            val now = System.currentTimeMillis()
            val delayToNextMinute = 60_000 - (now % 60_000)
            delay(delayToNextMinute)
            currentTime = getFormattedTime(System.currentTimeMillis(), offsetSeconds)
        }
    }


    Text(
        text = stringResource(string.local_time, currentTime),
        style = descriptionSubHeader,
        modifier = modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}