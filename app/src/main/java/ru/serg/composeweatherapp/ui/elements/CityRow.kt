package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import ru.serg.composeweatherapp.data.data.CityItem

@Composable
fun CityRow(cityItem: CityItem){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = cityItem.name?:"", style = MaterialTheme.typography.subtitle2)
        Text(text = cityItem.country?:"", style = MaterialTheme.typography.subtitle2)
    }
}