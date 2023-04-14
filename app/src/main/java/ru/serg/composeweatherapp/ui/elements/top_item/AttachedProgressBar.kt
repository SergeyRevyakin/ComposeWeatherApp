package ru.serg.composeweatherapp.ui.elements.top_item

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AttachedProgressBar(
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .height(4.dp)
            .fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 3.dp)
            )
        }

        AnimatedVisibility(
            visible = isLoading,
            enter = slideInHorizontally(animationSpec = tween(500), initialOffsetX = { -it }),
            exit = slideOutHorizontally(animationSpec = tween(500), targetOffsetX = { it })
        ) {
            LinearProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }
    }
}
