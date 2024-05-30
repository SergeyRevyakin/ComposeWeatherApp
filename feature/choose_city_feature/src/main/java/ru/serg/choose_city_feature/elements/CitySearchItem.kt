package ru.serg.choose_city_feature.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.gradientBorder
import ru.serg.model.CityItem
import ru.serg.strings.R.string

@Composable
fun CitySearchItem(
    cityItemState: State<CityItem>,
    onDelete: ((CityItem) -> Unit),
    onItemClick: ((CityItem) -> Unit),
    modifier: Modifier = Modifier
) {
    val cityItem = cityItemState.value
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
            contentDescription = stringResource(id = string.accessibility_desc_delete_favourite_icon),
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
        val city = remember {
            mutableStateOf(
                CityItem(name = "Moscow", "")
            )
        }
        CitySearchItem(cityItemState = city, onDelete = {}, onItemClick = {})
    }
}