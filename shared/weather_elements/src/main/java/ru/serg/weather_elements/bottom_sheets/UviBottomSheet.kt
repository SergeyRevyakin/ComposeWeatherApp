package ru.serg.weather_elements.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.common.mapUvIndex
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.descriptionSubHeader
import ru.serg.designsystem.theme.headerStyle
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.strings.R.string

@Composable
fun UviBottomSheet(
    value: Double
) {
    val uvIndex = mapUvIndex(value)
    Column(
        modifier = Modifier
            .padding(12.dp)
    )
    {
        Text(
            text = stringResource(id = string.ultraviolet_index),
            style = headerStyle,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )

        val descriptionText = buildAnnotatedString {
            append(stringResource(id = uvIndex.descriptionId))
            append(" - ")
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(value.toString())
            }
        }

        Text(
            text = descriptionText,
            style = descriptionSubHeader,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )

        Text(
            text = stringResource(id = uvIndex.detailsId),
            style = settingsSubText,
            modifier = Modifier.padding(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewUviBottomSheet() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        UviBottomSheet(
            9.0
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLightUviBottomSheet() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        UviBottomSheet(
            6.0
        )
    }
}


