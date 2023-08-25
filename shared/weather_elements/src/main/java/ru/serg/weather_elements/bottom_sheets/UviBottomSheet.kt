package ru.serg.weather_elements.bottom_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.common.UvIndex
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.descriptionSubHeader
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.weather_elements.R

@Composable
fun UviBottomSheet(
    uvIndex: UvIndex,
    onDismiss: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.surface
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colors.onBackground),
                RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onDismiss()
            }
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ultraviolet_index),
            style = headerStyle,
            modifier = Modifier
                .headerModifier(),
            textAlign = TextAlign.Center
        )

        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )


            Text(
                text = stringResource(id = uvIndex.descriptionId),
                style = descriptionSubHeader,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
            )
        Text(
                text = stringResource(id = uvIndex.detailsId),
                style = settingsSubText,
                modifier = Modifier
                    .padding(8.dp)
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
            UvIndex.VERY_HIGH,
            onDismiss = {},
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
            UvIndex.HIGH,
            onDismiss = {},
        )
    }
}


