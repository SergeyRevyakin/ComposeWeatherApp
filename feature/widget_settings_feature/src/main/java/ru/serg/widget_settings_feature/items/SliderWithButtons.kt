package ru.serg.widget_settings_feature.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderWithButtons(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> (Unit),
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(imageVector = Icons.Default.Remove, contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable {
                    if ((value - 1) >= 0) {
                        onValueChange(value - 1)
                    } else {
                        onValueChange(0f)
                    }
                }
                .padding(8.dp))

        Slider(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            colors = SliderDefaults.colors().copy(disabledActiveTickColor = Color.Cyan),
            valueRange = valueRange,
            modifier = Modifier.weight(1f),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    thumbTrackGapSize = 2.dp,
                    drawStopIndicator = null
                )
            }
        )

        Icon(imageVector = Icons.Default.Add, contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable {
                    onValueChange(value + 1)
                }
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewSliderWithButtons() {
    ComposeWeatherAppTheme {
        SliderWithButtons(value = 24f, valueRange = 12f..36f, {})
    }
}