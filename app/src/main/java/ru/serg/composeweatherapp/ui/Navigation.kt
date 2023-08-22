package ru.serg.composeweatherapp.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.serg.composeweatherapp.ui.screens.choose_city.ChooseCityScreen
import ru.serg.composeweatherapp.ui.screens.city_weather_screen.CityWeatherScreen
import ru.serg.composeweatherapp.ui.screens.main_screen.MainScreen
import ru.serg.composeweatherapp.ui.screens.main_screen.MainViewModel
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.common.ScreenNames
import ru.serg.settings_feature.screen.SettingsScreen

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalFoundationApi::class, FlowPreview::class,
    ExperimentalCoroutinesApi::class
)
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
        composable(
            ScreenNames.MAIN_SCREEN,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(300)
                )
            },
        ) {
            MainScreen(
                viewModule,
                { navController.navigate(ScreenNames.CHOOSE_CITY_SCREEN) },
                { navController.navigate(ScreenNames.SETTINGS_SCREEN) }
            )
        }

        // Choose City screen
        composable(
            ScreenNames.CHOOSE_CITY_SCREEN,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(500)
                )
            }
        ) {
            ChooseCityScreen(
                navController = navController
            )
        }

        composable(
            ScreenNames.SETTINGS_SCREEN,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(500)
                )
            }
        ) {
            SettingsScreen(
                navController = navController
            )
        }

        composable(
            route = "${ScreenNames.CITY_WEATHER_SCREEN}/{${Constants.CITY_ITEM}}",
            arguments = listOf(navArgument(Constants.CITY_ITEM) {
                type = NavType.StringType
            }),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(500)
                )
            }
        ) {
            CityWeatherScreen(navController = navController)
        }
    }
}
