package ru.serg.composeweatherapp.ui.elements.city_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.utils.Constants

@Composable
fun CityRow(
    cityItem: CityItem,
    onItemClick: ((CityItem) -> Unit),
    onAddClick: ((CityItem) -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke(cityItem)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${cityItem.name}, ${cityItem.country}",
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onAddClick.invoke(cityItem)
                }) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add to favourite",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .testTag(Constants.TestTag.CITY_ROW_TEST_TAG)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCityRow() {
    CityRow(cityItem = CityItem("Moscow", "Ru", 0.0, 0.0, false), onItemClick = {}, onAddClick = {})
}