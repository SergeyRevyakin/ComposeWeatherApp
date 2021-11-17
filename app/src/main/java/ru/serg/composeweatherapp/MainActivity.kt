package ru.serg.composeweatherapp

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeWeatherAppTheme {
        Greeting("Android")
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreenAnimation()
        }
        // Main Screen
//        composable("main_screen") {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(text = "Main Screen", color = Color.Black, fontSize = 24.sp)
//            }
//        }
    }
}


@Composable
fun SplashScreen(rotationDegrees:Float = 0f) {
//    val scale = remember {
//        Animatable(0f)
//    }
//
//    // AnimationEffect
//    LaunchedEffect(key1 = true) {
//        scale.animateTo(
//            targetValue = 0.7f,
//            animationSpec = tween(
//                durationMillis = 800,
//                easing = {
//                    OvershootInterpolator(4f).getInterpolation(it)
//                })
//        )
//        delay(3000L)
//        navController.navigate("main_screen")
//    }
//
//    // Image
//    Box(contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()) {
//        Image(painter = painterResource(id = R.drawable.ic_sun),
//            contentDescription = "Logo",
//            modifier = Modifier.scale(scale.value))
//    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_sun),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxSize(0.4f)
                .align(Alignment.Center)
                .rotate(rotationDegrees)
        )

    }
}


@Composable
fun SplashScreenAnimation(){
    var currentRotation by remember {
        mutableStateOf(0f)
    }

    val rotation = remember {
        Animatable(currentRotation)
    }

    LaunchedEffect(true) {
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ) {
            currentRotation = value
        }
    }
    SplashScreen(rotation.value)
}