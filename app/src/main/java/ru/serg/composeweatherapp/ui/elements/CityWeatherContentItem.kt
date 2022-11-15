package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.ui.screens.DailyWeatherDetailsScreen
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle

@Composable
fun CityWeatherContentItem(
    weatherItem: WeatherItem,
    modifier: Modifier = Modifier
) {
    val hourlyWeatherListState = rememberLazyListState()

    val city = weatherItem.cityItem?.name.orEmpty()

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

        TodayWeatherCardItem(
            weatherIcon = weatherItem.weatherIcon,
            weatherDesc = weatherItem.weatherDescription.orEmpty(),
            currentTemp = weatherItem.currentTemp?.toInt(),
            feelsLikeTemp = weatherItem.feelsLike?.toInt()
                ?: 0,
            windDirection = weatherItem.windDirection,
            windSpeed = weatherItem.windSpeed?.toInt(),
            humidity = weatherItem.humidity ?: 0,
            pressure = weatherItem.pressure ?: 0,
            timestamp = weatherItem.lastUpdatedTime
        )

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
            val list =
                weatherItem.hourlyWeatherList
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
            val list =
                weatherItem.dailyWeatherList
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
