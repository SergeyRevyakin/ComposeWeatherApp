package ru.serg.composeweatherapp.ui.screens.pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.ui.elements.DailyWeatherItem
import ru.serg.composeweatherapp.ui.elements.HourlyWeatherItem
import ru.serg.composeweatherapp.ui.elements.TodayWeatherCardItem
import ru.serg.composeweatherapp.ui.screens.DailyWeatherDetailsScreen
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.IconMapper
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ScreenState


@Composable
fun PagerScreen(
    cityItem: CityItem?,
    startLoading: Boolean,
    modifier: Modifier = Modifier,
) {

    val viewModel: PagerViewModel =
        ru.serg.composeweatherapp.utils.hiltViewModel(key = cityItem?.name)

    if (startLoading) {
        viewModel.initialize(cityItem)
    }

    AnimatedVisibility(
        visible = viewModel.screenState == ScreenState.LOADING,
        enter = fadeIn(
            animationSpec = tween(500)
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        )
    ) {
        LoadingScreen()
    }

    AnimatedVisibility(
        visible = viewModel.screenState == ScreenState.DATA,
        enter = slideInVertically(
            initialOffsetY = { 1500 },
            animationSpec = tween(500)
        ),
        exit = fadeOut(
            animationSpec = tween(0)
        )

    ) {
        ContentScreen(
            cityItem = cityItem,
            viewModel = viewModel,
            modifier = modifier
        )
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

@Composable
fun ContentScreen(
    cityItem: CityItem?,
    viewModel: PagerViewModel,
    modifier: Modifier = Modifier
) {
    val hourlyWeatherListState = rememberLazyListState()

    val city = cityItem?.name
        ?: (viewModel.simpleWeather.value as? NetworkResult.Success<WeatherResponse>)?.data?.name.orEmpty()

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = viewModel.screenState == ScreenState.LOADING),
        onRefresh = {
            viewModel.initialize()
        }) {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {

            item {
                Text(
                    text = city,
                    style = MaterialTheme.typography.headerStyle,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                TodayWeatherCardItem(
                    weatherIcon = IconMapper.map(
                        viewModel.simpleWeather.value.data?.weather?.first()?.id
                            ?: 0
                    ),
                    weatherDesc = viewModel.simpleWeather.value.data?.weather?.first()?.description.orEmpty(),
                    currentTemp = viewModel.simpleWeather.value.data?.main?.temp?.toInt() ?: 0,
                    feelsLikeTemp = viewModel.simpleWeather.value.data?.main?.feelsLike?.toInt()
                        ?: 0,
                    windDirection = viewModel.simpleWeather.value.data?.wind?.deg ?: 0,
                    windSpeed = viewModel.simpleWeather.value.data?.wind?.speed?.toInt() ?: 0,
                    humidity = viewModel.simpleWeather.value.data?.main?.humidity ?: 0,
                    pressure = viewModel.simpleWeather.value.data?.main?.pressure ?: 0,
                )
            }

            item {
                Text(
                    text = "Hourly",
                    style = MaterialTheme.typography.headerStyle,
                    modifier = Modifier
                        .headerModifier()
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    state = hourlyWeatherListState,
                ) {
                    val list = viewModel.oneCallWeather.value.data?.hourly ?: listOf()
                    items(list) {
                        it?.let { HourlyWeatherItem(item = it) }

                    }
                }
            }

            item {
                Text(
                    text = "Daily",
                    style = MaterialTheme.typography.headerStyle,
                    modifier = Modifier
                        .headerModifier()
                        .padding(top = 12.dp)
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val list = viewModel.oneCallWeather.value.data?.daily ?: listOf()
                    list.forEachIndexed { _, daily ->
                        if (daily != null) {

                            var isDailyItemOpen by remember {
                                mutableStateOf(false)
                            }

                            DailyWeatherItem(item = daily) { isDailyItemOpen = true }
                            if (isDailyItemOpen) {
                                DailyWeatherDetailsScreen(
                                    daily = daily,
                                    modifier = Modifier
                                ) {
                                    isDailyItemOpen = !isDailyItemOpen
                                }
                            }

                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


