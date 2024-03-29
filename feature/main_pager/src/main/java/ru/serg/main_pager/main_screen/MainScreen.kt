package ru.serg.main_pager.main_screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import dev.shreyaspatil.permissionFlow.utils.launch
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.designsystem.common.ErrorItem
import ru.serg.designsystem.common.SunLoadingScreen
import ru.serg.designsystem.top_item.PagerTopItem
import ru.serg.main_pager.CommonScreenState
import ru.serg.main_pager.openAppSystemSettings
import ru.serg.main_pager.updated_pager.PagerScreen
import ru.serg.weather_elements.elements.NoCitiesMainScreenItem

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToChooseCity: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {

    val permissionLauncher = rememberPermissionFlowRequestLauncher()

    val screenState by viewModel.citiesWeather.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        (screenState as? CommonScreenState.Success)?.updatedWeatherList?.size ?: 0
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setPageNumber(pagerState.currentPage)
    }

    val state = rememberPullToRefreshState()

    val isLoading by viewModel.isLoading

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.refresh()
        }
    }

    LaunchedEffect(isLoading) {
        state.endRefresh()
    }

    Box(Modifier.nestedScroll(state.nestedScrollConnection)) {

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
                hasFavourite =
                (screenState as? CommonScreenState.Success)?.updatedWeatherList?.any { it.cityItem.isFavorite }
                    ?: false
            )

            val context = LocalContext.current

            AnimatedVisibility(visible = screenState is CommonScreenState.Empty) {

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

            AnimatedVisibility(
                visible = screenState is CommonScreenState.Loading,
                enter = fadeIn(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(
                    animationSpec = tween(300)
                )
            ) {
                SunLoadingScreen()
            }

            AnimatedVisibility(visible = screenState is CommonScreenState.Success) {

                HorizontalPager(
                    modifier = Modifier.fillMaxWidth(),
                    state = pagerState,
                    userScrollEnabled = true,
                    reverseLayout = false,
                    outOfBoundsPageCount = 1,
                    pageContent = {
                        PagerScreen(
                            weatherItem = (screenState as CommonScreenState.Success).updatedWeatherList[it],
                        )
                    }
                )
            }

            AnimatedVisibility(visible = screenState is CommonScreenState.Error) {
                ErrorItem(onRefreshClick = { viewModel.initCitiesWeatherFlow() })
            }
        }

        PullToRefreshContainer(
            state = state,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colorScheme.primary,
        )
    }
}

