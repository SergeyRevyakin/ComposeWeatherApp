package ru.serg.city_weather.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.designsystem.common.ErrorItem
import ru.serg.designsystem.top_item.TopItem
import ru.serg.weather_elements.ScreenState
import ru.serg.weather_elements.weather_screen.CityWeatherContentItem

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
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { navController.navigateUp() },
            isLoading = screenState is ScreenState.Loading
        )

        AnimatedVisibility(visible = screenState is ScreenState.Error) {
            val errorText = (screenState as? ScreenState.Error)?.message
            ErrorItem(errorText = errorText)
        }

        AnimatedVisibility(
            visible = screenState is ScreenState.Success,
            enter = fadeIn(
                animationSpec = tween(300)
            ),
            exit = fadeOut(
                animationSpec = tween(300)
            )

        ) {
            val weatherItem = (screenState as? ScreenState.Success)?.weatherItem

            weatherItem?.let {
                CityWeatherContentItem(
                    weatherItem = weatherItem,
                    modifier = modifier
                )
            }
        }


    }
}
