package ru.serg.widget_settings_feature.utils

import android.content.Context
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceTheme
import ru.serg.designsystem.theme.primaryDark
import ru.serg.designsystem.theme.primaryLight

@Composable
fun fillColorSet(context: Context): Set<Color> {
    val result = mutableSetOf<Color>()
    if (isTiramisuOrAbove()) {
        result.apply {
            GlanceTheme.colors.apply {
                add(primary.getColor(context))
                add(onPrimary.getColor(context))
                add(primaryContainer.getColor(context))
                add(inversePrimary.getColor(context))
                add(onPrimaryContainer.getColor(context))
                add(secondary.getColor(context))
                add(secondaryContainer.getColor(context))
                add(onSecondary.getColor(context))
                add(onSecondaryContainer.getColor(context))
            }
        }
    }

    result.apply {
        add(Color.White)
        add(Color.Black)
        add(Color.Blue)
        add(Color.Yellow)
        add(Color.Cyan)
        add(Color.Green)
        add(Color.Gray)
        add(Color.DarkGray)
        add(Color.LightGray)

        add(primaryDark)
        add(primaryLight)
    }

    return result
}


@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun isTiramisuOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
