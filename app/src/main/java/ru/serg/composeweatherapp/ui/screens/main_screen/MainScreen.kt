package ru.serg.composeweatherapp.ui.screens.main_screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.shreyaspatil.permissionFlow.utils.launch
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import ru.serg.composeweatherapp.ui.elements.common.NoCitiesMainScreenItem
import ru.serg.composeweatherapp.ui.elements.top_item.PagerTopItem
import ru.serg.composeweatherapp.ui.screens.pager.PagerScreen
import ru.serg.composeweatherapp.utils.openAppSystemSettings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToChooseCity: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {

    val permissionLauncher = rememberPermissionFlowRequestLauncher()

    val citiesList by viewModel.citiesList.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        viewModel.citiesList.value.size
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        PagerTopItem(
            leftIconImageVector = Icons.Rounded.Search,
            rightIconImageVector = Icons.Rounded.Settings,
            onLeftIconClick = navigateToChooseCity,
            onRightIconClick = navigateToSettings,
            isLoading = viewModel.isLoading.value,
            pagerState = pagerState,
            hasFavourite = viewModel.citiesList.collectAsState().value.any {
                it?.isFavorite ?: true
            }
        )

        val context = LocalContext.current

        AnimatedVisibility(visible = citiesList.isEmpty() && !viewModel.isLoading.value) {

            NoCitiesMainScreenItem(
                onSearchClick = navigateToChooseCity,
                onRequestPermissionClick = {
                    permissionLauncher.launch(
                        ACCESS_COARSE_LOCATION,
                        ACCESS_FINE_LOCATION
                    )
                },
                goToSettings = { context.openAppSystemSettings() }
            )
        }

        AnimatedVisibility(visible = citiesList.isNotEmpty()) {

            viewModel.citiesList.collectAsState().value.size
            //To prevent preload of next page,
            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
                userScrollEnabled = true,
                reverseLayout = false,
                beyondBoundsPageCount = 0,
                pageContent = {
                    PagerScreen(
                        cityItem = citiesList[it],
                        pagerState.currentPage == it, //To prevent preload of next page,
                        viewModel.isLoading
                    )
                }
            )
        }
    }
}

