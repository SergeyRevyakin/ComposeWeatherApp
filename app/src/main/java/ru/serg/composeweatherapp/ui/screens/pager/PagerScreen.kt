package ru.serg.composeweatherapp.ui.screens.pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import ru.serg.composeweatherapp.ui.elements.DailyWeatherItem
import ru.serg.composeweatherapp.ui.elements.HourlyWeatherItem
import ru.serg.composeweatherapp.ui.elements.TodayWeatherCardItem
import ru.serg.composeweatherapp.ui.screens.DailyWeatherDetailsScreen
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.NetworkResult


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
        visible = viewModel.localWeatherItem.value is NetworkResult.Loading,
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
        visible = viewModel.localWeatherItem.value is NetworkResult.Success,
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

    val city = viewModel.localWeatherItem.value.data?.cityItem?.name.orEmpty()

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = viewModel.localWeatherItem.value is NetworkResult.Loading),
        onRefresh = {
            viewModel.initialize(cityItem)
        }) {

        val columnState = rememberScrollState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(columnState, true)
        ) {

            Text(
                text = city,
                style = MaterialTheme.typography.headerStyle,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            val weatherItemState = viewModel.localWeatherItem.value.data

            weatherItemState?.let {
                TodayWeatherCardItem(
                    weatherIcon = weatherItemState.weatherIcon,
                    weatherDesc = weatherItemState.weatherDescription.orEmpty(),
                    currentTemp = weatherItemState.currentTemp?.toInt(),
                    feelsLikeTemp = weatherItemState.feelsLike?.toInt()
                        ?: 0,
                    windDirection = weatherItemState.windDirection,
                    windSpeed = weatherItemState.windSpeed?.toInt(),
                    humidity = weatherItemState.humidity ?: 0,
                    pressure = weatherItemState.pressure ?: 0,
                    timestamp = weatherItemState.lastUpdatedTime
                )
            }

            Text(
                text = "Hourly",
                style = MaterialTheme.typography.headerStyle,
                modifier = Modifier
                    .headerModifier()
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = hourlyWeatherListState,
            ) {
                val list = viewModel.localWeatherItem.value.data?.hourlyWeatherList ?: listOf()
                items(list) {
                    HourlyWeatherItem(item = it)
                }
            }

            Text(
                text = "Daily",
                style = MaterialTheme.typography.headerStyle,
                modifier = Modifier
                    .headerModifier()
                    .padding(top = 12.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val list = viewModel.localWeatherItem.value.data?.dailyWeatherList ?: listOf()
                list.forEach { daily ->

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

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


