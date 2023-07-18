package ru.serg.composeweatherapp.ui.screens.city_weather_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.composeweatherapp.ui.elements.common.ErrorItem
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.ui.elements.weather_screen.UpdatedCityWeatherContentItem
import ru.serg.composeweatherapp.ui.screens.ScreenState

@Composable
fun CityWeatherScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: CityWeatherViewModel = hiltViewModel()
) {

    val screenState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopItem(
            header = "Current weather in",
            leftIconImageVector = Icons.Rounded.ArrowBack,
            onLeftIconClick = { navController.navigateUp() },
            isLoading = screenState is ScreenState.Loading
        )

        AnimatedVisibility(visible = screenState is ScreenState.Error) {
            val errorText = (screenState as? ScreenState.Error)?.message
            ErrorItem(errorText = errorText)
        }

        AnimatedVisibility(
            visible = screenState is ScreenState.Success,
            enter = slideInVertically(
                initialOffsetY = { 1500 },
                animationSpec = tween(500)
            ),
            exit = fadeOut(
                animationSpec = tween(0)
            )

        ) {
            val weatherItem = (screenState as? ScreenState.Success)?.weatherItem

            weatherItem?.let {
                UpdatedCityWeatherContentItem(
                    weatherItem = weatherItem,
                    modifier = modifier
                )
            }
        }


    }
}
