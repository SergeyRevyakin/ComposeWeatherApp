package ru.serg.weather_elements.elements

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.serg.designsystem.common.CardButton
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.strings.R.string
import ru.serg.weather_elements.animatedBlur
import ru.serg.weather_elements.bottom_sheets.DialogContainer
import ru.serg.weather_elements.bottom_sheets.WelcomeBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoCitiesMainScreenItem(
    onSearchClick: (() -> Unit),
    onRequestPermissionClick: (() -> Unit),
    goToSettings: (() -> Unit),
    hasWelcomeBottomSheet: Boolean,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    if (hasWelcomeBottomSheet) {
        LaunchedEffect(Unit) {
            sheetState.show()
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 120.dp, bottom = 24.dp)
            .animatedBlur(sheetState.targetValue != SheetValue.Hidden),
    ) {
        Text(
            text = stringResource(id = string.no_weather_data_available),
            style = headerStyle,
            modifier = Modifier
                .headerModifier()
                .fillMaxWidth()
        )

        CardButton(
            buttonText = stringResource(id = string.please_allow_us_to_get_location_access),
            image = Icons.Rounded.LocationSearching
        ) {
            onRequestPermissionClick()
        }

        Text(
            text = stringResource(id = string.click_here_to_change_it_in_settings),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .clickable {
                    goToSettings()
                }
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        CardButton(
            buttonText = stringResource(id = string.or_find_city_manually),
            image = Icons.Rounded.Search
        ) {
            onSearchClick()
        }

        if (sheetState.isVisible) {
            DialogContainer(
                onDismiss = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                sheetState = sheetState
            ) {
                WelcomeBottomSheet(
                    onSearchClick = onSearchClick,
                    onEnableLocationClick = onRequestPermissionClick
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNoCitiesMainScreenItem() {
    ComposeWeatherAppTheme {
        Scaffold {
            NoCitiesMainScreenItem({}, {}, {}, true, Modifier.padding(it))
        }
    }
}

@Preview
@Composable
fun PreviewNoCitiesMainScreenItem2() {
    ComposeWeatherAppTheme {
        Scaffold {
            NoCitiesMainScreenItem({}, {}, {}, false, Modifier.padding(it))
        }
    }
}