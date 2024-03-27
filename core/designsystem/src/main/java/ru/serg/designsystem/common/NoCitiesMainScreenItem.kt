package ru.serg.designsystem.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.strings.R.string

@Composable
fun NoCitiesMainScreenItem(
    onSearchClick: (() -> Unit)? = null,
    onRequestPermissionClick: (() -> Unit)? = null,
    goToSettings: (() -> Unit)? = null,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp, bottom = 24.dp),
    ) {
        Text(
            text = stringResource(id = string.no_weather_data_available),
            style = headerStyle,
            modifier = Modifier
                .headerModifier()
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        )

        CardButton(
            buttonText = stringResource(id = string.please_allow_us_to_get_location_access),
            image = Icons.Rounded.LocationSearching
        ) {
            onRequestPermissionClick?.invoke()
        }

        Text(
            text = stringResource(id = string.click_here_to_change_it_in_settings),
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(bottom = 32.dp)
                .clickable {
                    goToSettings?.invoke()
                },
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        CardButton(
            buttonText = stringResource(id = string.or_find_city_manually),
            image = Icons.Rounded.Search
        ) {
            onSearchClick?.invoke()
        }

    }
}

@Preview
@Composable
fun PreviewNoCitiesMainScreenItem() {
    NoCitiesMainScreenItem()
}