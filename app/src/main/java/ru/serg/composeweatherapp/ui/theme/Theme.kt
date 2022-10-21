package ru.serg.composeweatherapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryDark,
    primaryVariant = primaryDark,
    secondary = primaryDark,
    secondaryVariant = primaryDark,
//    background = Color.Black,
//    onBackground = Color.White,
//    surface = Color.Black,
//    onSurface = Color.White

)

private val LightColorPalette = lightColors(
    primary = primaryLight,
    primaryVariant = primaryLight,
    secondary = primaryLight,
    secondaryVariant = primaryLight
//    background = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Yellow,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSurface = Color.Black,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeWeatherAppTheme(
    darkTheme: MutableState<Boolean> = mutableStateOf(false),
    content: @Composable() () -> Unit
) {
    val isDark = remember {
        darkTheme
    }
    val colors = if (isDark.value) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun PreviewDarkTheme(content: @Composable () -> Unit){
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}