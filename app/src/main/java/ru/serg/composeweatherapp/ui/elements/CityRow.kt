package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.data.data.CityItem

@Composable
fun CityRow(cityItem: CityItem, onClick: ((CityItem) -> Unit)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick.invoke(cityItem)
            },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = cityItem.name ?: "", style = MaterialTheme.typography.subtitle2)
        Text(text = cityItem.country ?: "", style = MaterialTheme.typography.subtitle2)
    }
}