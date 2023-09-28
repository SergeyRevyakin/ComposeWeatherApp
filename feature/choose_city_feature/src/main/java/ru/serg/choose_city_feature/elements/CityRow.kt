package ru.serg.choose_city_feature.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.model.CityItem
import ru.serg.strings.R.string

@Composable
fun CityRow(
    cityItem: CityItem,
    onItemClick: ((CityItem) -> Unit),
    onAddClick: ((CityItem) -> Unit),
    isAddedToFavorites: State<Boolean>,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = MaterialTheme.colors.surface
        .copy(alpha = 0.9f)
        .compositeOver(Color.White)

    val borderColor =
        animateColorAsState(
            targetValue = if (isAddedToFavorites.value) MaterialTheme.colors.primary else backgroundColor,
            label = "color",
            animationSpec = tween(300, easing = FastOutLinearInEasing)
        )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onItemClick.invoke(cityItem)
            }
            .border(
                width = 2.dp,
                color = borderColor.value,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                backgroundColor
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${cityItem.name}, ${cityItem.country}",
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)

        )

        AnimatedVisibility(
            visible = !isAddedToFavorites.value,
            enter = fadeIn(tween(300, easing = FastOutLinearInEasing)),
            exit = fadeOut(tween(300, easing = FastOutLinearInEasing))
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(id = string.accessibility_desc_add_to_favourite_icon),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        onAddClick.invoke(cityItem)
                    }
                    .padding(8.dp)

            )
        }

    }
}

@Preview
@Composable
fun PreviewCityRow() {
    val isFavourite = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme {
        CityRow(
            cityItem = CityItem("Moscow", "Ru", 0.0, 0.0, false),
            onItemClick = {},
            onAddClick = {},
            isAddedToFavorites = isFavourite
        )
    }
}

@Preview
@Composable
fun PreviewCityRowDark() {
    val isDark = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDark) {
        val isFavourite = remember {
            mutableStateOf(true)
        }
        CityRow(
            cityItem = CityItem("Moscow", "Ru", 0.0, 0.0, false),
            onItemClick = {},
            onAddClick = {},
            isAddedToFavorites = isFavourite
        )
    }
}

@Preview
@Composable
fun PreviewCityRowDarkNotFav() {
    val isDark = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDark) {
        val isFavourite = remember {
            mutableStateOf(false)
        }
        CityRow(
            cityItem = CityItem("Moscow", "Ru", 0.0, 0.0, false),
            onItemClick = {},
            onAddClick = {},
            isAddedToFavorites = isFavourite
        )
    }
}