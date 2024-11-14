package ru.serg.weather_elements.weather_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.serg.designsystem.simple_items.DailyWeatherItem
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.model.DailyWeather
import ru.serg.model.WeatherItem
import ru.serg.strings.R.string
import ru.serg.weather_elements.animatedBlur
import ru.serg.weather_elements.bottom_sheets.AirQualityBottomSheet
import ru.serg.weather_elements.bottom_sheets.DailyWeatherBottomSheet
import ru.serg.weather_elements.bottom_sheets.DialogContainer
import ru.serg.weather_elements.bottom_sheets.UviBottomSheet
import ru.serg.weather_elements.elements.AlertCardItem
import ru.serg.weather_elements.elements.SunriseSunsetItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityWeatherContentItem(
    weatherItem: WeatherItem,
    modifier: Modifier = Modifier,
    viewModel: CityWeatherContentItemViewModel = hiltViewModel()
) {
    val hourlyWeatherListState = rememberLazyListState()

    val city = weatherItem.cityItem.name

    val columnState = rememberScrollState()

    val units by viewModel.units.collectAsStateWithLifecycle()

    val isAlertsEnabled by viewModel.isAlertsEnabled.collectAsStateWithLifecycle()

    var showUviDetailsBottomSheet by remember {
        mutableStateOf(false)
    }

    var showAqiDetailsBottomSheet by remember {
        mutableStateOf(false)
    }

    var showDailyWeatherBottomSheet by remember {
        mutableStateOf(false)
    }

    var dailyWeather: DailyWeather? by remember {
        mutableStateOf(null)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(columnState, true)
            .animatedBlur(sheetState.targetValue == SheetValue.Expanded)

    ) {
        Text(
            text = city,
            style = headerStyle,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        if (isAlertsEnabled) {
            weatherItem.alertList.forEach {
                AlertCardItem(it)
            }
        }

        TodayWeatherCardItem(
            weatherItem = weatherItem.hourlyWeatherList.first(),
            units = units,
            lastUpdatedTime = weatherItem.cityItem.lastTimeUpdated,
            showUviInfo = {
                showUviDetailsBottomSheet = true
            },
            showAqiInfo = {
                showAqiDetailsBottomSheet = true
            }
        )

        val todayWeather = weatherItem.dailyWeatherList.first()

        SunriseSunsetItem(sunriseTime = todayWeather.sunrise, sunsetTime = todayWeather.sunset)

        Text(
            text = stringResource(id = string.hourly),
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
                HourlyWeatherItem(item = list[it], units = units)
            }
        }

        Text(
            text = stringResource(id = string.daily),
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
                    dailyWeather = daily
                    showDailyWeatherBottomSheet = true
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Spacer(Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
    }

    if (showDailyWeatherBottomSheet) {
        DialogContainer(
            onDismiss = { showDailyWeatherBottomSheet = !showDailyWeatherBottomSheet },
            sheetState = sheetState
        ) {

            dailyWeather?.let {

                DailyWeatherBottomSheet(
                    daily = it,
                    units = units,
                )
            }
        }
    }

    if (showUviDetailsBottomSheet) {
        DialogContainer(
            onDismiss = { showUviDetailsBottomSheet = !showUviDetailsBottomSheet },
            sheetState = sheetState
        ) {
            UviBottomSheet(
                value = weatherItem.hourlyWeatherList.first().uvi
            )
        }
    }

    if (showAqiDetailsBottomSheet) {
        DialogContainer(
            onDismiss = { showAqiDetailsBottomSheet = !showAqiDetailsBottomSheet },
            sheetState = sheetState
        ) {
            AirQualityBottomSheet(
                weatherItem.hourlyWeatherList.first().airQuality
            )
        }
    }
}
