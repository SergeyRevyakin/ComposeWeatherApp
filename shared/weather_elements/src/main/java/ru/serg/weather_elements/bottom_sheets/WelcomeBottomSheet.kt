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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.strings.R.string
import ru.serg.weather_elements.elements.IconRowButton
import ru.serg.weather_elements.getWelcomeText

@Composable
fun WelcomeBottomSheet(
    onDismiss: () -> Unit,
    onSearchClick: () -> Unit,
    onEnableLocationClick: () -> Unit
) {
    val gradient = Brush.linearGradient(
        listOf(
            MaterialTheme.colors.background.copy(alpha = 0.8f)
                .compositeOver(MaterialTheme.colors.onBackground),
            MaterialTheme.colors.background
        ),
    )
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .background(
                gradient,
                RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onDismiss()
            }

    ) {
        Text(
            text = stringResource(id = string.welcome),
            style = headerStyle,
            modifier = Modifier
                .headerModifier(),
            textAlign = TextAlign.Center
        )

        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 1.dp,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 12.dp)
        )

        Text(
            text = getWelcomeText(),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(16.dp)
                .padding(horizontal = 12.dp)
        )

        IconRowButton(
            Icons.Default.Search,
            stringResource(id = string.search_city),
        ) {
            onSearchClick()
        }

        Spacer(modifier = Modifier.height(12.dp))

        IconRowButton(
            Icons.Default.LocationSearching,
            stringResource(id = string.turn_on_geolocation),
        ) {
            onEnableLocationClick()
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeBottomSheet() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {

        WelcomeBottomSheet(
            {},
            {},
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLightWelcomeBottomSheet() {
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {

        WelcomeBottomSheet(
            {},
            {},
            {}
        )
    }
}