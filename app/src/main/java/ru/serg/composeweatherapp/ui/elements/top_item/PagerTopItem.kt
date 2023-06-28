package ru.serg.composeweatherapp.ui.elements.top_item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTopItem(
    rightIconImageVector: ImageVector? = null,
    leftIconImageVector: ImageVector? = null,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
    isLoading: Boolean = false,
    pagerState: PagerState,
    hasFavourite: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .padding(start = 16.dp, end = 24.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        onLeftIconClick?.invoke()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                leftIconImageVector?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Search",
                        modifier = Modifier
                    )
                }
            }

            if (pagerState.pageCount > 1) {
                PageIndicator(
                    itemsCount = pagerState.pageCount,
                    selectedItem = pagerState.currentPage,
                    hasFavourite = hasFavourite,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }

            Column(
                Modifier
                    .padding(start = 24.dp, end = 16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        onRightIconClick?.invoke()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                rightIconImageVector?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Search",
                    )
                }
            }
        }

        AttachedProgressBar(isLoading = isLoading)

        ConnectivityStatus()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewPagerTopItem() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        PagerTopItem(
            leftIconImageVector = Icons.Rounded.Search,
            rightIconImageVector = Icons.Rounded.Settings,
            onLeftIconClick = {},
            onRightIconClick = {},
            pagerState = rememberPagerState(1, 0f) {
                3
            },
            hasFavourite = true
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewNoItemPagerTopItem() {
    ComposeWeatherAppTheme() {
        PagerTopItem(
            leftIconImageVector = Icons.Rounded.Search,
            rightIconImageVector = Icons.Rounded.Settings,
            onLeftIconClick = {},
            onRightIconClick = {},
            pagerState = rememberPagerState(0, 0f) {
                0
            },
            hasFavourite = false
        )
    }
}
