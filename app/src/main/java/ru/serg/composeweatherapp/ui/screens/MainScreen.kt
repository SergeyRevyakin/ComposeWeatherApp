package ru.serg.composeweatherapp.ui.screens

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.ui.DailyWeatherDetailsScreen
import ru.serg.composeweatherapp.ui.MainViewModel
import ru.serg.composeweatherapp.ui.elements.DailyWeatherItem
import ru.serg.composeweatherapp.ui.elements.HourlyWeatherItem
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.Ext.getTemp
import ru.serg.composeweatherapp.utils.IconMapper
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ScreenState
import ru.serg.composeweatherapp.worker.WeatherWorker


@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel, navigateToChooseCity: () -> Unit, modifier: Modifier = Modifier) {

    val hourlyWeatherListState = rememberLazyListState()


    if (viewModel.oneCallWeather.value is NetworkResult.Success) {

        val result = viewModel.oneCallWeather.value as NetworkResult.Success<OneCallResponse>

        val city =
            (viewModel.simpleWeather.value as? NetworkResult.Success<WeatherResponse>)?.data?.name

        val context = LocalContext.current

        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = (viewModel.screenState.value == ScreenState.LOADING)),
            onRefresh = {
                viewModel.initialize()
                WeatherWorker.setupPeriodicWork(context)
            }) {

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = (city) ?: "",
                        style = MaterialTheme.typography.headerStyle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .headerModifier()
                            .clickable {
                                navigateToChooseCity.invoke()
                            }
                    )
                }

                item {

                    Image(
                        painter = painterResource(id = IconMapper.map(viewModel.simpleWeather.value.data?.weather?.first()?.id?:0)),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                        contentDescription = "Weather icon",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
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
                                    text = viewModel.counter.toString(),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
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
                        Column() {
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
                                        DailyWeatherDetailsScreen(daily = daily, modifier = Modifier) {
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
}

@Preview(showBackground = true)
@Composable
fun Preview() {
//    MainScreen(viewModel = hiltViewModel())
}

