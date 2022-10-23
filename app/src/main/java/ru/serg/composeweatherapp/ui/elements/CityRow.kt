package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.data.data.CityItem

@Composable
fun CityRow(cityItem: CityItem, onClick: ((CityItem) -> Unit)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(cityItem)
            }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "${cityItem.name}, ${cityItem.country}", fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun CityRowPreview() {
    CityRow(cityItem = CityItem("Moscow", "Ru", 0.0, 0.0), onClick = {})
}