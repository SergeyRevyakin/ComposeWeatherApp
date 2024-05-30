@file:OptIn(
    FlowPreview::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalFoundationApi::class
)

package ru.serg.composeweatherapp

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.serg.choose_city_feature.screen.ChooseCityScreen
import ru.serg.city_weather.screen.CityWeatherScreen
import ru.serg.common.ScreenNames
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.main_pager.main_screen.MainScreen
import ru.serg.main_pager.main_screen.MainViewModel
import ru.serg.settings_feature.screen.SettingsScreen
import ru.serg.widget_settings_feature.screen.WidgetSettingsScreen

@Composable
fun Navigation(
    viewModule: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(
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
            val onLeftClick = remember {
                { navController.navigate(ScreenNames.CHOOSE_CITY_SCREEN) }
            }
            val onRightClick = remember {
                { navController.navigate(ScreenNames.SETTINGS_SCREEN) }
            }

            MainScreen(
                viewModule,
                onLeftClick,
                onRightClick
            )
        }

        // Choose City screen
        composable(
            ScreenNames.CHOOSE_CITY_SCREEN,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
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
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeOut(
                    animationSpec = tween(300)
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
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            SettingsScreen(
                navController = navController
            )
        }

        composable(
            ScreenNames.WIDGET_SETTINGS_SCREEN,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            }
        ) {
            WidgetSettingsScreen(
                navController = navController
            )
        }

        composable(
            route = "${ScreenNames.CITY_WEATHER_SCREEN}/{${Constants.CITY_ITEM}}",
            arguments = listOf(navArgument(Constants.CITY_ITEM) {
                type = NavType.StringType
            }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            CityWeatherScreen(navController = navController)
        }
    }
}
