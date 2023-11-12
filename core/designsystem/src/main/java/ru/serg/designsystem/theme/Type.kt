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

val buttonText: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 20.sp,
            letterSpacing = 1.5.sp
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