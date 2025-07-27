package ru.serg.composeweatherapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.FlowPreview
import ru.serg.choose_city_feature.screen.ChooseCityScreen
import ru.serg.navigation.nav3.CityWeatherScreenNav
import ru.serg.navigation.nav3.SearchScreenNav

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun EntryProviderBuilder<*>.SearchScreenNavigation(
    backStack: SnapshotStateList<NavKey>,
    navigateBack: () -> Unit,
    animationDuration: Int
) = entry<SearchScreenNav>(
    metadata = NavDisplay.transitionSpec {
        slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(animationDuration)
        ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(animationDuration)
                )
    } + NavDisplay.popTransitionSpec {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(animationDuration)
        ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
    } + NavDisplay.predictivePopTransitionSpec {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(animationDuration)
        ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
    }
) {

    ChooseCityScreen(
        navigateBack = navigateBack,
        navigateToCityWeather = { cityItem ->
            backStack.add(
                CityWeatherScreenNav(
                    cityItem = cityItem
                )
            )
        },
    )
}