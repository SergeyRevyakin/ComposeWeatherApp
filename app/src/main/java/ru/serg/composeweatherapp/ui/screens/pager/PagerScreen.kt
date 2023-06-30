package ru.serg.composeweatherapp.ui.screens.pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.ui.elements.CityWeatherContentItem
import ru.serg.composeweatherapp.ui.elements.PullRefreshBox
import ru.serg.composeweatherapp.ui.elements.common.ErrorItem
import ru.serg.composeweatherapp.ui.elements.common.SunLoadingScreen
import ru.serg.composeweatherapp.ui.screens.ScreenState

@Composable
fun PagerScreen(
    cityItem: CityItem?,
    startLoading: Boolean,
    isRefreshing: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {

    val viewModel: PagerViewModel =
        ru.serg.composeweatherapp.utils.hiltViewModel(key = cityItem?.name)

    if (startLoading) {
        viewModel.initialize(cityItem)
    }

    val uiState by viewModel.uiState.collectAsState()

    val refreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(refreshing) {
        isRefreshing.value = refreshing
    }

    AnimatedVisibility(
        visible = uiState is ScreenState.Loading,
        enter = fadeIn(
            animationSpec = tween(500)
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        )
    ) {
        SunLoadingScreen()
    }

    AnimatedVisibility(visible = uiState is ScreenState.Error) {
        val errorText = (uiState as? ScreenState.Error)?.message
        ErrorItem(errorText = errorText)
    }

    AnimatedVisibility(
        visible = uiState is ScreenState.Success,
        enter = slideInVertically(
            initialOffsetY = { 1500 },
            animationSpec = tween(500)
        ),
        exit = fadeOut(
            animationSpec = tween(0)
        )

    ) {
        val weatherItem = (uiState as? ScreenState.Success)?.weatherItem

        PullRefreshBox(
            refreshing = refreshing,
            onRefresh = { viewModel.refresh(cityItem) }
        ) {
            weatherItem?.let {
                CityWeatherContentItem(
                    weatherItem = weatherItem,
                    modifier = modifier
                )
            }
        }
    }
}

