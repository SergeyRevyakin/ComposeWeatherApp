package ru.serg.settings_feature.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment

@Composable
fun CollapsingContainer(
    isVisible: State<Boolean>,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible.value,
        enter = expandVertically(
            animationSpec = tween(600),
            expandFrom = Alignment.Top,
            initialHeight = { 0 }
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(300)
        ) + slideInVertically(
            initialOffsetY = { -200 },
            animationSpec = tween(600)
        ),
        exit = shrinkVertically(
            animationSpec = tween(600),
            shrinkTowards = Alignment.Top,
            targetHeight = { 0 }
        ) + fadeOut(
            targetAlpha = 0.3f,
            animationSpec = tween(300)
        ) + slideOutVertically(
            targetOffsetY = { -200 },
            animationSpec = tween(600)
        )
    ) {
        Column {
            content()
        }
    }
}