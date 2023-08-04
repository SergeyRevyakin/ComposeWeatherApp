package ru.serg.composeweatherapp.ui.elements.city_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serg.model.CityItem
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import ru.serg.composeweatherapp.ui.theme.gradientBorder

@Composable
fun CitySearchItem(
    cityItem: CityItem,
    onDelete: ((CityItem) -> Unit),
    onItemClick: ((CityItem) -> Unit),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .gradientBorder(1, 16)
            .clip(RoundedCornerShape(16.dp))
            .height(48.dp)
            .clickable {
                onItemClick.invoke(cityItem)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = cityItem.name,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .padding(start = 12.dp, end = 6.dp),
            fontSize = 16.sp
        )

        Icon(
            Icons.Rounded.Close,
            contentDescription = stringResource(id = R.string.accessibility_desc_delete_favourite_icon),
            modifier = Modifier
                .padding(vertical = 6.dp)
                .padding(end = 6.dp)
                .size(32.dp)
                .clip(CircleShape)
                .clickable {
                    onDelete.invoke(cityItem)
                }
                .padding(2.dp)
        )
    }
}

@Preview
@Composable
fun PreviewCitySearchItem() {
    ComposeWeatherAppTheme {
        CitySearchItem(cityItem = CityItem(name = "Moscow", ""), onDelete = {}, onItemClick = {})
    }
}