package ru.serg.composeweatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = primaryDark,
//    primaryVariant = Purple700,
//    secondary = Teal200,
//    background = Color.Black,
//    onBackground = Color.White,

)

private val LightColorPalette = lightColors(
    primary = primaryLight,
//    primaryVariant = Color.DarkGray,
//    secondary = Teal200,
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
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

//    systemUiController.isNavigationBarVisible = false
//    systemUiController.isStatusBarVisible = false

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}