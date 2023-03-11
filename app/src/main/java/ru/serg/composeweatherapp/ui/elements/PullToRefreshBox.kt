package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun PullRefreshBox(
    modifier: Modifier = Modifier,
    refreshing: Boolean,
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val isRefreshingWorkaround by remember { mutableStateOf(refreshing) }

    val state = rememberPullRefreshState(isRefreshingWorkaround, onRefresh)

    Box(
        modifier = modifier.pullRefresh(state),
    ) {
        content()
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshingWorkaround,
            state = state,
            contentColor = MaterialTheme.colors.primary,
        )
    }
}