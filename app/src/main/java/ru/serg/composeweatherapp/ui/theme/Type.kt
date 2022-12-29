package ru.serg.composeweatherapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
)

@OptIn(ExperimentalTextApi::class)
val headerStyle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colors.onBackground,
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.primary,
                ),
                tileMode = TileMode.Mirror,
                start = Offset(30f, 50f),
                end = Offset(250f, 350f)
            ),


            )
    }

val descriptionSubHeader: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 24.sp,
            letterSpacing = 2.sp
        )
    }