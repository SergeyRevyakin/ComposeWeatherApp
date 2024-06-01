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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.serg.choose_city_feature.screen.ChooseCityScreen
import ru.serg.city_weather.screen.CityWeatherScreen
import ru.serg.main_pager.main_screen.MainScreen
import ru.serg.main_pager.main_screen.MainViewModel
import ru.serg.navigation.CityFinderScreen
import ru.serg.navigation.CityWeatherScreen
import ru.serg.navigation.MainScreen
import ru.serg.navigation.ParcCityItem
import ru.serg.navigation.SettingsScreen
import ru.serg.navigation.WidgetSettingsScreen
import ru.serg.navigation.serializableType
import ru.serg.settings_feature.screen.SettingsScreen
import ru.serg.widget_settings_feature.screen.WidgetSettingsScreen
import kotlin.reflect.typeOf

@Composable
fun Navigation(
    viewModule: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreen,
        typeMap = mapOf(typeOf<ParcCityItem>() to serializableType<ParcCityItem>())
    ) {
        val animationDuration = 300

        // Main Screen
        composable<MainScreen>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            },
        ) {
            val onLeftClick = remember {
                { navController.navigate(CityFinderScreen) }
            }
            val onRightClick = remember {
                { navController.navigate(SettingsScreen) }
            }

            MainScreen(
                viewModule,
                onLeftClick,
                onRightClick
            )
        }

        // Choose City screen
        composable<CityFinderScreen>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ChooseCityScreen(
                navController = navController
            )
        }

        //Settings screen
        composable<SettingsScreen>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(animationDuration)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) {
            SettingsScreen(
                navController = navController
            )
        }

        //Widget settings screen
        composable<WidgetSettingsScreen>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(animationDuration)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            WidgetSettingsScreen(
                navController = navController
            )
        }

        composable<CityWeatherScreen>(
            typeMap = CityWeatherScreen.typeMap,

            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(animationDuration)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(animationDuration)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            CityWeatherScreen(navController = navController)
        }
    }
}