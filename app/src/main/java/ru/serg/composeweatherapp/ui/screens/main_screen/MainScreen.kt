package ru.serg.composeweatherapp.ui.screens.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.ui.elements.DailyWeatherItem
import ru.serg.composeweatherapp.ui.elements.HourlyWeatherItem
import ru.serg.composeweatherapp.ui.elements.MainScreenTopItem
import ru.serg.composeweatherapp.ui.screens.DailyWeatherDetailsScreen
import ru.serg.composeweatherapp.ui.theme.gradientBorder
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.Ext.getTemp
import ru.serg.composeweatherapp.utils.IconMapper
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ScreenState
import ru.serg.composeweatherapp.worker.WeatherWorker


@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToChooseCity: () -> Unit,
    modifier: Modifier = Modifier
) {

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
            viewModel = viewModel,
            navigateToChooseCity = navigateToChooseCity,
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
    viewModel: MainViewModel,
    navigateToChooseCity: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hourlyWeatherListState = rememberLazyListState()

    val city =
        (viewModel.simpleWeather.value as? NetworkResult.Success<WeatherResponse>)?.data?.name.orEmpty()

    val context = LocalContext.current

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = viewModel.screenState == ScreenState.LOADING),
        onRefresh = {
            viewModel.initialize()
            WeatherWorker.setupPeriodicWork(context)
        }) {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            item {
                MainScreenTopItem(
                    cityName = city,
                    onSearchCityClick = navigateToChooseCity,
                    onSettingsClick = {}
                )
            }

            item {
                val gradient = Brush.linearGradient(
                    listOf(Color.DarkGray, MaterialTheme.colors.background),
                )

                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = 10.dp,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .gradientBorder()
                        .wrapContentHeight()
                        .clickable {
                            viewModel.initialize()
                        }
                ) {
                    Column(
                        modifier = Modifier.background(gradient)
                    ) {

                        Icon(
                            painter = painterResource(
                                id = IconMapper.map(
                                    viewModel.simpleWeather.value.data?.weather?.first()?.id
                                        ?: 0
                                )
                            ),
                            contentDescription = "Weather icon",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                        .padding(horizontal = 24.dp)
                                ) {
                                    Text(
                                        text = "Temperature: ${getTemp(viewModel.simpleWeather.value.data?.main?.temp)}",
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                        .padding(horizontal = 24.dp)
                                ) {
                                    Text(
                                        text = "Feels like: ${getTemp(viewModel.simpleWeather.value.data?.main?.feelsLike)}",
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }

                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                        .padding(horizontal = 24.dp)
                                ) {
                                    Text(
                                        text = viewModel.simpleWeather.value.data?.weather?.first()?.main.orEmpty(),
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                        .padding(horizontal = 24.dp)
                                ) {
                                    Text(
                                        text = "TODO",
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }
                            }

                        }
                    }
                }
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
                )
            }
            item {
                Card(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Column {
                        val list = viewModel.oneCallWeather.value.data?.daily ?: listOf()
                        list.forEachIndexed { index, daily ->
                            if (daily != null) {
                                var isDailyItemOpen by remember {
                                    mutableStateOf(false)
                                }
                                val color = if (isSystemInDarkTheme()) {
                                    if (index % 2 == 0) Color.Gray else Color.DarkGray
                                } else {
                                    if (index % 2 == 0) Color.LightGray else Color.Gray
                                }
                                DailyWeatherItem(item = daily, color) { isDailyItemOpen = true }
                                if (isDailyItemOpen) {
                                    DailyWeatherDetailsScreen(
                                        daily = daily,
                                        modifier = Modifier
                                    ) {
                                        isDailyItemOpen = false//!isDailyItemOpen
                                    }
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    MainScreen(viewModel = hiltViewModel(), navigateToChooseCity = {})
}

