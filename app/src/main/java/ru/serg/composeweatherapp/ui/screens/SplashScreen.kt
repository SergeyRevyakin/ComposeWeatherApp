package ru.serg.composeweatherapp.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ScreenState


@Composable
fun SplashScreen(rotationDegrees: Float = 0f) {

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
fun SplashScreenAnimation(
    viewModule: MainViewModel,
    onLoadSuccess: () -> Unit
) {
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

    if (viewModule.screenState.value == ScreenState.DATA){
        LaunchedEffect(Unit){
            onLoadSuccess.invoke()
        }
    }
}