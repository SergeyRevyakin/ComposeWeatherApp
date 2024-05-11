package ru.serg.weather_elements.bottom_sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.descriptionSubHeader
import ru.serg.designsystem.theme.headerStyle
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.model.AirQuality
import ru.serg.model.enums.AirQualityEUIndex
import ru.serg.strings.R.string
import ru.serg.weather_elements.getAqiColorByIndex

@Composable
fun AirQualityBottomSheet(
    airQuality: AirQuality
) {
    val airQualityEUIndex =
        AirQualityEUIndex.entries.firstOrNull { it.id == airQuality.getEUPollutionIndex() }
            ?: AirQualityEUIndex.UNKNOWN

    val accentColor = getAqiColorByIndex(index = airQualityEUIndex.id)
    val uriHandler = LocalUriHandler.current
    val link = stringResource(id = string.aqi_eu_link)

    Column(
        modifier = Modifier
            .padding(12.dp)
    )
    {
        Text(
            text = stringResource(id = string.aqi_index),
            style = headerStyle,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = accentColor
        )

        Text(
            text = stringResource(id = airQualityEUIndex.indexDescription),
            style = descriptionSubHeader,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )

        Text(
            text = stringResource(id = string.aqi_based_on_eu_calc),
            style = settingsSubText,
            modifier = Modifier
                .padding(12.dp)
                .clickable {
                    uriHandler.openUri(link)
                }
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = accentColor
        )

        Text(
            text = stringResource(id = string.aqi_general_population),
            fontSize = 16.sp,
            modifier = Modifier.padding(PaddingValues(start = 12.dp, end = 12.dp, top = 12.dp))
        )

        Text(
            text = stringResource(id = airQualityEUIndex.generalPopulationEffect),
            style = settingsSubText,
            modifier = Modifier.padding(12.dp)
        )

        Text(
            text = stringResource(id = string.aqi_sensitive_population),
            fontSize = 16.sp,
            modifier = Modifier.padding(PaddingValues(start = 12.dp, end = 12.dp, top = 12.dp))
        )

        Text(
            text = stringResource(id = airQualityEUIndex.sensitivePopulationEffect),
            style = settingsSubText,
            modifier = Modifier.padding(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewUAirQualityBottomSheet() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        AirQualityBottomSheet(
            AirQuality.blankAirQuality()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLightAirQualityBottomSheet() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        AirQualityBottomSheet(
            AirQuality.blankAirQuality()
        )
    }
}


