package ru.serg.composeweatherapp.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    viewModule: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen",
    ) {
        composable("splash_screen") {
            SplashScreenAnimation(viewModule, onLoadSuccess = {navController.navigate("main_screen"){
                popUpTo(0)
            } })
        }
        // Main Screen
        composable("main_screen") {
            MainScreen(viewModule)
        }
    }
}
