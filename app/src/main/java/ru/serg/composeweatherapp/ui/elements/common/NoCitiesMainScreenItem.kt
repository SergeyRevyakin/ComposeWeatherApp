package ru.serg.composeweatherapp.ui.elements.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .fillMaxWidth(),
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 12.dp)
                .clickable {
                    onRequestPermissionClick?.invoke()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Please allow us to get location access",
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .weight(1f)
            )

            Icon(
                imageVector = Icons.Rounded.LocationSearching,
                contentDescription = null,
                Modifier.size(48.dp),
                tint = MaterialTheme.colors.primary
            )
        }
        Text(
            text = "Click here to change it in settings",
            fontSize = 22.sp,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .padding(horizontal = 24.dp)
                .clickable {
                    goToSettings?.invoke()
                }
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clickable {
                    onSearchClick?.invoke()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                Modifier.size(48.dp),
                tint = MaterialTheme.colors.primary
            )

            Text(
                text = "Or find city manually",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewNoCitiesMainScreenItem() {
    NoCitiesMainScreenItem()
}