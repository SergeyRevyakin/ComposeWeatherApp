package ru.serg.composeweatherapp.ui.elements.city_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.ui.theme.gradientBorder
import ru.serg.composeweatherapp.utils.Constants

@Composable
fun CitySearchItem(
    cityItem: CityItem,
    onDelete: ((CityItem) -> Unit),
    onItemClick: ((CityItem) -> Unit),
) {
    Card(
        modifier = Modifier
            .gradientBorder(1, 16)
            .height(48.dp),
        shape = RoundedCornerShape(16.dp)

    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .clickable {
                    onItemClick.invoke(cityItem)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cityItem.name,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                fontSize = 16.sp
            )

            Icon(
                Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onDelete.invoke(cityItem)
                    }
                    .testTag(Constants.TestTag.CITY_SEARCH_DELETE_TEST_TAG)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCitySearchItem() {
    CitySearchItem(cityItem = CityItem(name = "Moscow", ""), onDelete = {}, onItemClick = {})
}