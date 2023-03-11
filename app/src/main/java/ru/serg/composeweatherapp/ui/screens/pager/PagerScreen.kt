package ru.serg.composeweatherapp.ui.screens.pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.ui.elements.CityWeatherContentItem
import ru.serg.composeweatherapp.ui.elements.PullRefreshBox
import ru.serg.composeweatherapp.ui.elements.common.ErrorItem
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
        LoadingScreen()
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

@Composable
fun LoadingScreen() {
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

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_sun),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxSize(0.4f)
                .align(Alignment.Center)
                .rotate(rotation.value)
        )
    }
}