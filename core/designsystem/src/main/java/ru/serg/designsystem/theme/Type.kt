package ru.serg.designsystem.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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

val headerStyle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
//            brush = Brush.linearGradient(
//                colors = listOf(
//                    MaterialTheme.colors.onBackground,
//                    MaterialTheme.colors.primary,
//                    MaterialTheme.colors.primary,
//                ),
//                tileMode = TileMode.Mirror,
//                start = Offset(30f, 50f),
//                end = Offset(250f, 350f)
//            ),
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

val settingsSubText: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 16.sp,
            color = Color.Gray,
        )
    }