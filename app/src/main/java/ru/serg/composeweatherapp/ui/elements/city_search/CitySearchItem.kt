package ru.serg.composeweatherapp.ui.elements.city_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.ui.theme.gradientBorder

@Composable
fun CitySearchItem(
    cityItem: CityItem,
    onDelete: ((CityItem) -> Unit)
) {
    Card(
        modifier = Modifier
            .gradientBorder(1, 16)
            .wrapContentHeight()

    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Text(
                text = cityItem.name,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Icon(
                Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onDelete.invoke(cityItem)
                    }
            )
        }
    }
}