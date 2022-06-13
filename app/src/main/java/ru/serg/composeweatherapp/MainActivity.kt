package ru.serg.composeweatherapp

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.serg.composeweatherapp.data.remote.WeatherResult
import ru.serg.composeweatherapp.ui.MainScreen
import ru.serg.composeweatherapp.ui.MainViewModel
import ru.serg.composeweatherapp.ui.SplashScreenAnimation
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
        setContent {
            ComposeWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation()
                }
            }
        }

        hideSystemUI()
    }

    fun hideSystemUI() {

        actionBar?.hide()

//        WindowCompat.setDecorFitsSystemWindows(window, false)

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        } else {
//            window.insetsController?.apply {
//                hide(WindowInsets.Type.statusBars())
//                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        }
    }
}

@Composable
fun Navigation(
    viewModule: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    viewModule.initialize()
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

