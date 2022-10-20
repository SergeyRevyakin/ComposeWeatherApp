package ru.serg.composeweatherapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.FlowPreview
import ru.serg.composeweatherapp.ui.screens.choose_city.ChooseCityScreen
import ru.serg.composeweatherapp.ui.screens.main_screen.MainScreen
import ru.serg.composeweatherapp.ui.screens.main_screen.MainViewModel
import ru.serg.composeweatherapp.ui.screens.settings.SettingsScreen
import ru.serg.composeweatherapp.utils.ScreenNames


@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun Navigation(
    viewModule: MainViewModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = ScreenNames.MAIN_SCREEN,
    ) {

        // Main Screen
        composable(ScreenNames.MAIN_SCREEN,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(1000)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(500)
                )
            }
        ) {
            MainScreen(
                viewModule,
                { navController.navigate(ScreenNames.CHOOSE_CITY_SCREEN) },
                { navController.navigate(ScreenNames.SETTINGS_SCREEN) }
            )
        }

        // Choose City screen
        composable(ScreenNames.CHOOSE_CITY_SCREEN,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1500 },
                    animationSpec = tween(1000)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -1400 },
                    animationSpec = tween(1000)
                ) + fadeOut(animationSpec = tween(800))
            }
        ) {
            ChooseCityScreen()
        }

        composable(
            ScreenNames.SETTINGS_SCREEN,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(1000)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(800)
                ) + fadeOut(animationSpec = tween(600))
            }
        ) {
            SettingsScreen(
                navController = navController
            )
        }
    }
}
