package ru.serg.widget_settings_feature.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme

@Composable
fun SliderWithButtons(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> (Unit),
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(imageVector = Icons.Default.Remove, contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable {
                    onValueChange(value - 1)
                }
                .padding(8.dp))

        Slider(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            valueRange = valueRange,
            modifier = Modifier.weight(1f)
        )

        Icon(imageVector = Icons.Default.Add, contentDescription = null,
            tint = MaterialTheme.colors.primary,
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