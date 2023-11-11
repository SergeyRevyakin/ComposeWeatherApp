package ru.serg.widgets

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.glance.material3.ColorProviders

object NonMaterialGlanceTheme {

    val colors = ColorProviders(
        light = lightColorScheme(primary = Color.White),
        dark = darkColorScheme(primary = Color.White)
    )
}