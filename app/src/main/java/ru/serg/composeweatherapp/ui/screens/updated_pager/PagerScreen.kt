package ru.serg.composeweatherapp.ui.screens.updated_pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.serg.composeweatherapp.data.dto.UpdatedWeatherItem
import ru.serg.composeweatherapp.ui.elements.common.ErrorItem
import ru.serg.composeweatherapp.ui.elements.common.SunLoadingScreen
import ru.serg.composeweatherapp.ui.elements.weather_screen.CityWeatherContentItem

@Composable
fun PagerScreen(
    weatherItem: UpdatedWeatherItem,
    modifier: Modifier = Modifier,
) {

    AnimatedVisibility(
        visible = weatherItem.dailyWeatherList.isEmpty() && weatherItem.hourlyWeatherList.isEmpty(),
        enter = fadeIn(
            animationSpec = tween(300)
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        )
    ) {
        SunLoadingScreen()
    }

    AnimatedVisibility(visible = weatherItem == null) {
        val errorText = "Error"
        ErrorItem(errorText = errorText)
    }

    AnimatedVisibility(
        visible = weatherItem.dailyWeatherList.isNotEmpty() && weatherItem.hourlyWeatherList.isNotEmpty(),
        enter = slideInVertically(
            initialOffsetY = { 1500 },
            animationSpec = tween(500)
        ),
        exit = fadeOut(
            animationSpec = tween(0)
        )

    ) {

        CityWeatherContentItem(
            weatherItem = weatherItem,
            modifier = modifier
        )

    }
}

