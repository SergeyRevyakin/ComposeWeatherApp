package ru.serg.designsystem.top_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
            HorizontalDivider(
                modifier = Modifier.padding(top = 3.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        AnimatedVisibility(
            visible = isLoading,
            enter = slideInHorizontally(animationSpec = tween(500), initialOffsetX = { -it }),
            exit = slideOutHorizontally(animationSpec = tween(500), targetOffsetX = { it })
        ) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }
    }
}
