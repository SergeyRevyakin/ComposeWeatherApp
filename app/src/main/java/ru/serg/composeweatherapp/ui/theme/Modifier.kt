package ru.serg.composeweatherapp.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min

fun Modifier.headerModifier() = this
    .fillMaxWidth()
    .padding(24.dp)

fun Modifier.gradientBorder(borderWidth: Int = 2, cornerRadius: Int = 24) =
    composed { border(

        BorderStroke(borderWidth.dp, Brush.linearGradient(
            listOf(
                MaterialTheme.colors.primary,
//                MaterialTheme.colors.onBackground,
                MaterialTheme.colors.primary,
                MaterialTheme.colors.onBackground,
            ),
//            start = Offset(50f, 1f),
//            end = Offset(150f, 100f),


            tileMode = TileMode.Clamp
        )),
        shape = RoundedCornerShape(cornerRadius.dp)
    ) }
