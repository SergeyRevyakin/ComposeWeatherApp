package ru.serg.composeweatherapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import androidx.navigation3.ui.NavDisplay
import ru.serg.city_weather.screen.CityWeatherScreen
import ru.serg.navigation.nav3.CityWeatherScreenNav


@Composable
fun EntryProviderBuilder<*>.CityWeatherScreenNavigation(
    navigateBack: () -> Unit,
    animationDuration: Int
) =
    entry<CityWeatherScreenNav>(
        metadata = NavDisplay.transitionSpec {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(animationDuration)
            ) togetherWith
                    slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(animationDuration)
                    )
        } + NavDisplay.popTransitionSpec {
            slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(animationDuration)
            ) togetherWith
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(animationDuration)
                    )
        } + NavDisplay.predictivePopTransitionSpec {
            slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(animationDuration)
            ) togetherWith
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(animationDuration)
                    )
        }
    ) {
        CityWeatherScreen(
            cityItem = it.cityItem,
            navigateBack = navigateBack,
        )
    }