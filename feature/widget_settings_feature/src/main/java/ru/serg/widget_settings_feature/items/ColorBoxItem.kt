package ru.serg.widget_settings_feature.items

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme

@Composable
fun ColorBoxItem(
    colorC: Color,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: (Color) -> Unit = {}
) {
    val borderColor = animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary
        else Color.Transparent,
        label = ""
    )

    val boxColor = animateColorAsState(targetValue = colorC, label = "")

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(boxColor.value, RoundedCornerShape(8.dp))
            .border(
                2.dp,
                borderColor.value,
                RoundedCornerShape(8.dp)
            )
            .clickable {
                onClick(colorC)
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun ColorBoxItemPreview() {
    val isDark = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDark) {
        ColorBoxItem(colorC = Color.Red, isSelected = true)
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorBoxItemDarkPreview() {
    val isDark = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDark) {
        ColorBoxItem(colorC = Color.Blue)
    }
}