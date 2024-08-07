package ru.serg.weather_elements.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.weather_elements.getAqiColorByIndex

@Composable
fun PollutionDescriptionRow(
    pollutionName: String,
    pollutionDescription: String,
    wikiLink: String,
    pollutionValue: String,
    pollutionValueDesc: String,
    pollutionValueIndex: Int,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val accentColor = getAqiColorByIndex(pollutionValueIndex)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
                .clickable {
                    uriHandler.openUri(wikiLink)
                }
        ) {

            Column {
                Text(text = pollutionName)
                Text(
                    text = pollutionDescription,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            text = pollutionValue,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = pollutionValueDesc,
            color = accentColor,
            modifier = Modifier
                .weight(2f)
                .padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ComposeWeatherAppTheme {
        PollutionDescriptionRow(
            "NO2",
            "Nitrogen dioxide",
            "https://en.wikipedia.org/wiki/Nitrogen_dioxide",
            "12",
            "Moderate",
            3
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview2() {
    ComposeWeatherAppTheme {
        PollutionDescriptionRow(
            "NO2",
            "Nitrogen dioxide",
            "https://en.wikipedia.org/wiki/Nitrogen_dioxide",
            "11",
            "Good",
            1
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewDark() {
    val isDark = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDark) {
        PollutionDescriptionRow(
            "NO2",
            "Coarse particulate matter",
            "https://en.wikipedia.org/wiki/Nitrogen_dioxide",
            "1",
            "Very poor",
            5
        )
    }
}