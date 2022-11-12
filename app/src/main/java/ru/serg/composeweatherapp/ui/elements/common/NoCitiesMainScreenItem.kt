package ru.serg.composeweatherapp.ui.elements.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle

@Composable
fun NoCitiesMainScreenItem(
    onSearchClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp, bottom = 24.dp),
//        verticalArrangement = Arrangement.
    ) {
        Text(
            text = "No weather data available",
            style = MaterialTheme.typography.headerStyle,
            modifier = Modifier
                .headerModifier()
                .fillMaxWidth(),
//            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clickable {
                    onSearchClick?.invoke()
                }
        ) {

            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                Modifier.size(56.dp)
            )

            Text(
                text = "Please allow us get location access or find city manually",
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