package ru.serg.weather_elements.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.common.mapUvIndex
import ru.serg.designsystem.simple_items.CurrentTempItem
import ru.serg.designsystem.simple_items.ParamRowWithInfoItem
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.gradientBorder
import ru.serg.designsystem.utils.AnimWeather
import ru.serg.designsystem.utils.MockItems
import ru.serg.designsystem.utils.getTemp
import ru.serg.drawables.R.drawable
import ru.serg.model.HourlyWeather
import ru.serg.model.enums.AirQualityEUIndex
import ru.serg.model.enums.Units
import ru.serg.strings.R.string
import ru.serg.weather_elements.getFormattedLastUpdateDate
import kotlin.math.roundToInt

@Composable
fun TodayWeatherCardItem(
    weatherItem: HourlyWeather,
    units: Units,
    modifier: Modifier = Modifier,
    hasFrame: Boolean = true,
    lastUpdatedTime: Long = System.currentTimeMillis(),
    showUviInfo: () -> Unit = {},
    showAqiInfo: () -> Unit = {},
) {
    val gradient = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                .compositeOver(MaterialTheme.colorScheme.onBackground),
            MaterialTheme.colorScheme.background
        ),
    )

    val backgroundModifier = if (hasFrame) modifier.then(
        Modifier
            .padding(12.dp)
            .shadow(
                elevation = 10.dp,
                spotColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .gradientBorder()
            .background(gradient)
    )
    else modifier

    Column(
        modifier = backgroundModifier
            .fillMaxWidth()
            .wrapContentHeight()

    ) {
        Column {

            AnimWeather(targetState = weatherItem.weatherIcon) {
                Icon(
                    painter = painterResource(
                        id = it
                    ),
                    contentDescription = stringResource(id = string.accessibility_desc_weather_icon),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            CurrentTempItem(
                temp = weatherItem.currentTemp.roundToInt(),
                description = weatherItem.weatherDescription,
                units = units.tempUnits
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                ParamRowWithInfoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    paramIcon = drawable.ic_thermometer,
                    paramValue = stringResource(
                        string.feels_like,
                        getTemp(
                            temp = weatherItem.feelsLike,
                            stringResource(id = units.tempUnits)
                        )
                    ),
                )

                ParamRowWithInfoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(horizontal = 12.dp),
                    paramIcon = drawable.ic_day_sunny,
                    paramValue = stringResource(
                        id = string.uv_index_value,
                        stringResource(id = mapUvIndex(weatherItem.uvi).descriptionId)
                    ),
                    hasInfoButton = true,
                    onInfoClick = showUviInfo
                )

                val airQualityEUIndex by remember(weatherItem.airQuality) {
                    mutableStateOf(AirQualityEUIndex.entries.firstOrNull { it.id == weatherItem.airQuality.getEUPollutionIndex() }
                        ?: AirQualityEUIndex.UNKNOWN
                    )
                }

                ParamRowWithInfoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(horizontal = 12.dp),
                    paramIcon = drawable.ic_sandstorm,
                    paramValue = stringResource(
                        id = string.aqi_value,
                        stringResource(id = airQualityEUIndex.indexDescription)
                    ),
                    hasInfoButton = true,
                    onInfoClick = showAqiInfo
                )

                if (!hasFrame) {
                    ParamRowWithInfoItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        paramIcon = drawable.ic_rain_mix,
                        paramValue = stringResource(
                            string.precipitation_probability,
                            weatherItem.precipitationProbability
                        ),
                    )
                }

                HorizontalWeatherMoreInfoItem(item = weatherItem, units = units)

                if (lastUpdatedTime > 0 && hasFrame) {
                    AnimWeather(targetState = lastUpdatedTime) {
                        Text(
                            text = stringResource(
                                id = string.last_updated_value,
                                getFormattedLastUpdateDate(it, 0)
                            ),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayWeatherCardItemPreview() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        Scaffold {
            TodayWeatherCardItem(
                MockItems.getHourlyWeatherMockItem(),
                Units.METRIC,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayWeatherCardItemPreviewDark() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        Scaffold {
            TodayWeatherCardItem(
                MockItems.getHourlyWeatherMockItem(),
                Units.METRIC,
                hasFrame = false,
                modifier = Modifier.padding(it)
            )
        }
    }
}