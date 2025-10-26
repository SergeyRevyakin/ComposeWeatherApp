package ru.serg.settings_feature.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.PreviewDarkTheme
import ru.serg.strings.R.string
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourSliderItem(
    hours: List<Int> = listOf(1, 2, 4, 6, 8, 12, 24),
    stateValue: MutableState<Float> = mutableFloatStateOf(2f),
    onValueChanged: ((Int) -> Unit) = {}
) {
    val haptic = LocalHapticFeedback.current
    var previousValue by remember { mutableIntStateOf(stateValue.value.roundToInt()) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = string.how_often_update_weather_data),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val primaryColor = MaterialTheme.colorScheme.primary

        Box(contentAlignment = Alignment.Center) {
            Slider(
                value = stateValue.value,
                onValueChange = {
                    val newRoundedValue = it.roundToInt()
                    if (newRoundedValue != previousValue) {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        previousValue = newRoundedValue
                    }
                    stateValue.value = it
                },
                onValueChangeFinished = {
                    onValueChanged.invoke(stateValue.value.toInt())
                },
                valueRange = 0f..hours.size.minus(1).toFloat(),
                steps = hours.size.minus(2),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(primaryColor, CircleShape)
                    )
                },
                track = { sliderState ->
                    SliderDefaults.Track(
                        sliderState = sliderState,
                        thumbTrackGapSize = 2.dp
                    )
                }
            )
        }

        Text(
            text = stringResource(
                id = string.weather_will_be_updated_every_value,
                hours[stateValue.value.roundToInt()]
            ),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SliderPreview() {
    PreviewDarkTheme {
        HourSliderItem()
    }
}