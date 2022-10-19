package ru.serg.composeweatherapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val Typography.headerStyle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            
        )
    }

val Typography.descriptionSubHeader: TextStyle
@Composable
get() {
    return TextStyle(
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic
    )
}