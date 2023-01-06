package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.ui.screens.DailyWeatherDetailsScreen
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle

@Composable
fun CityWeatherContentItem(
    weatherItem: WeatherItem,
    modifier: Modifier = Modifier,
    viewModel: CityWeatherContentItemViewModel = hiltViewModel()
) {
    val hourlyWeatherListState = rememberLazyListState()

    val city = weatherItem.cityItem?.name.orEmpty()

    val columnState = rememberScrollState()

    val units =
        viewModel.units.collectAsState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(columnState, true)
    ) {

        Text(
            text = city,
            style = headerStyle,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        TodayWeatherCardItem(
//            weatherIcon = weatherItem.weatherIcon,
//            weatherDesc = weatherItem.weatherDescription.orEmpty(),
//            currentTemp = weatherItem.currentTemp?.toInt(),
//            feelsLikeTemp = weatherItem.feelsLike?.toInt()
//                ?: 0,
//            windDirection = weatherItem.windDirection,
//            windSpeed = weatherItem.windSpeed?.toInt(),
//            humidity = weatherItem.humidity ?: 0,
//            pressure = weatherItem.pressure ?: 0,
//            timestamp = weatherItem.lastUpdatedTime,
            weatherItem = weatherItem,
            units = units.value
        )

        Text(
            text = "Hourly",
            style = headerStyle,
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
                HourlyWeatherItem(item = it, units = units.value)
            }
        }

        Text(
            text = "Daily",
            style = headerStyle,
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

                DailyWeatherItem(item = daily, viewModel.units.value) { isDailyItemOpen = true }
                if (isDailyItemOpen) {
                    DailyWeatherDetailsScreen(
                        daily = daily,
                        units = units.value,
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
