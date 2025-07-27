package ru.serg.composeweatherapp

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.serg.composeweatherapp.navigation.CityWeatherScreenNavigation
import ru.serg.composeweatherapp.navigation.MainScreenNavigation
import ru.serg.composeweatherapp.navigation.SearchScreenNavigation
import ru.serg.composeweatherapp.navigation.SettingsScreenNavigation
import ru.serg.composeweatherapp.navigation.WidgetSettingsScreenNavigation
import ru.serg.main_pager.main_screen.MainViewModel
import ru.serg.navigation.nav3.MainScreenNav

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun NavGraph(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {

    val backStack = rememberNavBackStack(MainScreenNav)
    val animationDuration = 300
    var lastNavTime by remember { mutableLongStateOf(0L) }

    val navigateBack = {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastNavTime > animationDuration) {
            if (backStack.isNotEmpty()) {
                backStack.removeAt(backStack.lastIndex)
                lastNavTime = currentTime
            }
        }
    }

    NavDisplay(
        modifier = modifier,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(animationDuration)
            ) togetherWith
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(animationDuration)
                    )
        },
        popTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(animationDuration)
            ) togetherWith
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(animationDuration)
                    )
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(animationDuration)
            ) togetherWith
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(animationDuration)
                    )
        },
        backStack = backStack,
        entryProvider = entryProvider {
            MainScreenNavigation(
                backStack = backStack,
                viewModel = viewModel,
                animationDuration = animationDuration
            )

            SearchScreenNavigation(backStack, navigateBack, animationDuration)

            SettingsScreenNavigation(backStack, navigateBack)

            CityWeatherScreenNavigation(navigateBack, animationDuration)

            WidgetSettingsScreenNavigation(navigateBack)
        },
    )

}