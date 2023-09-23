package ru.serg.designsystem.top_item

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.strings.R.string

@Composable
fun PageIndicator(
    itemsCount: Int,
    selectedItem: Int,
    modifier: Modifier = Modifier,
    hasFavourite: Boolean = false
) {

    Row(
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {


        repeat(itemsCount) { iteration ->

            val color = animateColorAsState(
                targetValue = if (selectedItem == iteration) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onBackground.copy(0.4f)
                }, animationSpec = tween(300, easing = EaseInOut), label = "color"
            )

            val size = animateDpAsState(
                targetValue = if (selectedItem == iteration) {
                    16.dp
                } else {
                    10.dp
                }, animationSpec = tween(300, easing = EaseInOut),
                label = "size"
            )

            Icon(
                imageVector = if (iteration == 0 && hasFavourite) Icons.Rounded.MyLocation
                else Icons.Rounded.Circle,
                contentDescription = stringResource(id = string.accessibility_desc_position_icon),
                tint = color.value,
                modifier = Modifier
                    .padding(2.dp)
                    .size(size.value)
            )
        }
    }
}

@Preview
@Composable
fun PreviewPageIndicator() {
    ComposeWeatherAppTheme {
        PageIndicator(itemsCount = 3, selectedItem = 2)
    }
}