package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.ui.theme.headerStyle

@Composable
fun MainScreenTopItem(
    cityName: String,
    onSearchCityClick: (() -> Unit),
    onSettingsClick: (() -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .size(48.dp)
                .clickable {
                    onSearchCityClick.invoke()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier
            )
        }

        Text(
            text = cityName,
            style = MaterialTheme.typography.headerStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Column(
            Modifier
                .size(48.dp)
                .clickable {
                    onSettingsClick.invoke()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Rounded.Settings,
                contentDescription = "Search",
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    MainScreenTopItem(
        cityName = "Moscow",
        onSearchCityClick = { blanc() },
        onSettingsClick = { blanc() })
}

fun blanc() {

}