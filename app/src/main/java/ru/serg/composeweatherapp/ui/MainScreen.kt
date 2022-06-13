package ru.serg.composeweatherapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.serg.composeweatherapp.data.remote.WeatherResult

@Composable
fun MainScreen(viewModule: MainViewModel) {

    if (viewModule.weather.value is WeatherResult.Success) {

        val result = viewModule.weather.value as WeatherResult.Success

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = result.timezone, color = Color.Black, fontSize = 24.sp)
        }
    }

}