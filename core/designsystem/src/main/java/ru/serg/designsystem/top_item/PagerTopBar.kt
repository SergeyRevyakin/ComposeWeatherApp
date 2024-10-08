package ru.serg.designsystem.top_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagerTopBar(
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
    isLoading: Boolean = false,
    pagerState: PagerState,
    appBarState: TopAppBarScrollBehavior?,
    content: @Composable () -> Unit = {}
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                if (pagerState.pageCount > 1) {
                    PageIndicator(
                        itemsCount = pagerState.pageCount,
                        selectedItem = pagerState.currentPage,
                        hasFavourite = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            },
            navigationIcon = {
                if (onLeftIconClick != null) {
                    IconButton(onClick = onLeftIconClick) {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "")
                    }
                }
            },
            actions = {
                if (onRightIconClick != null) {
                    IconButton(onClick = onRightIconClick) {
                        Icon(imageVector = Icons.Rounded.Settings, contentDescription = "")
                    }
                }
            },
            scrollBehavior = appBarState,
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = expandVertically(animationSpec = tween(300)),
            exit = shrinkVertically(animationSpec = tween(300)),
        ) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }

        ConnectivityStatus()

        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewMaterialPagerTopBar() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        PagerTopBar(
            onLeftIconClick = {},
            onRightIconClick = {},
            pagerState = rememberPagerState(1, 0f) {
                3
            },
            appBarState = null
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewNoItemMaterialPagerTopBar() {
    ComposeWeatherAppTheme {
        PagerTopBar(
            onLeftIconClick = {},
            onRightIconClick = {},
            pagerState = rememberPagerState(0, 0f) {
                0
            },
            appBarState = null
        )
    }
}
