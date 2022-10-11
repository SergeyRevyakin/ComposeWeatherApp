package ru.serg.composeweatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.ui.elements.DailyWeatherItem
import ru.serg.composeweatherapp.ui.elements.HourlyWeatherItem
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ScreenState


@Composable
fun MainScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {

    val hourlyWeatherListState = rememberLazyListState()
    val dailyWeatherListState = rememberLazyListState()


    if (viewModel.oneCallWeather.value is NetworkResult.Success) {

        val result = viewModel.oneCallWeather.value as NetworkResult.Success<OneCallResponse>

        val city =
            (viewModel.simpleWeather.value as NetworkResult.Success<WeatherResponse>).data?.name

        val country = java.util.Locale.getDefault().displayCountry



        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = (viewModel.screenState.value == ScreenState.LOADING)),
            onRefresh = {
                viewModel.initialize()
            }) {

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
//                .verticalScroll(rememberScrollState())

            ) {
//            Row(modifier = Modifier.fillMaxWidth()) {

                item {
                    Text(
                        text = (city) ?: "",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    )
                }
//            }
//            val image = AnimatedImageVector.animatedVectorResource(R.drawable.ic_sunny_day)
//            val atEnd by remember { mutableStateOf(false) }
                item {

                    Image(
                        painter = painterResource(id = R.drawable.ic_sun),
                        contentDescription = "Weather icon",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 36.dp)
                            .padding(horizontal = 100.dp)
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
                                    text = "Temperature: ${viewModel.simpleWeather.value.data?.main?.temp.toString()}",
                                    color = Color.Black,
                                    fontSize = 16.sp,
//                            textAlign = TextAlign.Center,
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
                                    text = "Feels like: ${viewModel.simpleWeather.value.data?.main?.feelsLike.toString()}",
                                    color = Color.Gray,
                                    fontSize = 16.sp,
//                            textAlign = TextAlign.Center,
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
                                    color = Color.Gray,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
//                                        .padding(top = 16.dp)
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
                                    color = Color.Gray,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
//                                        .padding(top = 16.dp)
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
//                modifier = Modifier.padding(top = .dp)
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
                                    val color = if (index % 2 == 0) Color.White else Color.LightGray
                                    DailyWeatherItem(item = daily, color) { isDailyItemOpen = true }
                                    if (isDailyItemOpen){
                                        DailyWeatherDetails(daily = daily, modifier = Modifier){
                                            isDailyItemOpen = false
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
