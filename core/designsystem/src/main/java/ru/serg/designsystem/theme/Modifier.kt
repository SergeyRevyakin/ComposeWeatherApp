package ru.serg.designsystem.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

fun Modifier.headerModifier() = this
    .fillMaxWidth()
    .padding(24.dp)
    .padding(start = 12.dp)

fun Modifier.gradientBorder(borderWidth: Int = 2, cornerRadius: Int = 24) =
    composed {
        border(
            BorderStroke(
                borderWidth.dp, Brush.sweepGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary,
                    ),
                )
            ),
            shape = RoundedCornerShape(cornerRadius.dp)
        )
    }
