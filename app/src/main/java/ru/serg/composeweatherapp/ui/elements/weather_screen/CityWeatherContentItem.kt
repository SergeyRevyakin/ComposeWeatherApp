package ru.serg.composeweatherapp.ui.elements.weather_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.elements.CityWeatherContentItemViewModel
import ru.serg.composeweatherapp.ui.elements.SunriseSunsetItem
import ru.serg.composeweatherapp.ui.elements.bottom_sheets.BottomSheetMainScreenState
import ru.serg.composeweatherapp.ui.elements.bottom_sheets.MainScreenBottomSheet
import ru.serg.composeweatherapp.utils.weather_mapper.UviMapper
import ru.serg.designsystem.simple_items.DailyWeatherItem
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.model.UpdatedWeatherItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CityWeatherContentItem(
    weatherItem: UpdatedWeatherItem,
    modifier: Modifier = Modifier,
    viewModel: CityWeatherContentItemViewModel = hiltViewModel()
) {
    val hourlyWeatherListState = rememberLazyListState()

    val city = weatherItem.cityItem.name

    val columnState = rememberScrollState()

    val units = viewModel.units.collectAsState()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    var currentBottomSheet: BottomSheetMainScreenState? by remember {
        mutableStateOf(null)
    }

    val openBottomSheet: (BottomSheetMainScreenState) -> Unit = {
        currentBottomSheet = it
        coroutineScope.launch {
            sheetState.show()
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            currentBottomSheet?.let {
                MainScreenBottomSheet(screenState = it) {
                    coroutineScope.launch { sheetState.hide() }
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(24.dp),
        sheetElevation = 10.dp,
        sheetBackgroundColor = Color.Transparent,
    ) {

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
                weatherItem = weatherItem.hourlyWeatherList.first(),
                units = units.value,
                lastUpdatedTime = weatherItem.cityItem.lastTimeUpdated,
                showUviInfo = {
                    openBottomSheet(
                        BottomSheetMainScreenState.UviDetailsScreen(
                            UviMapper.map(weatherItem.hourlyWeatherList.first().uvi)
                        )
                    )
                }
            )

            val todayWeather = weatherItem.dailyWeatherList.first()

            SunriseSunsetItem(sunriseTime = todayWeather.sunrise, sunsetTime = todayWeather.sunset)

            Text(
                text = stringResource(id = R.string.hourly),
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
                items(list.size) {
                    HourlyWeatherItem(item = list[it], units = units.value)
                }
            }

            Text(
                text = stringResource(id = R.string.daily),
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

                    DailyWeatherItem(item = daily, viewModel.units.value) {
                        openBottomSheet(
                            BottomSheetMainScreenState.DailyWeatherScreen(
                                dailyWeather = daily,
                                units = units.value
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

    }
}