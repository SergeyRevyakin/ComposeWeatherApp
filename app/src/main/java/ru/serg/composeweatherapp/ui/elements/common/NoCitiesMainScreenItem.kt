package ru.serg.composeweatherapp.ui.elements.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle

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
            text = "No weather data available",
            style = MaterialTheme.typography.headerStyle,
            modifier = Modifier
                .headerModifier()
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        )

        CardButton(
            buttonText = "Please allow us to get location access",
            image = Icons.Rounded.LocationSearching
        ) {
            onRequestPermissionClick?.invoke()
        }

        Text(
            text = "Click here to change it in settings",
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(bottom = 32.dp)
                .clickable {
                    goToSettings?.invoke()
                },
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center
        )

        CardButton(
            buttonText = "Or find city manually",
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