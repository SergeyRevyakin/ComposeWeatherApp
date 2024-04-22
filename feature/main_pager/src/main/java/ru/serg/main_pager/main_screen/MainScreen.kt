package ru.serg.main_pager.main_screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import dev.shreyaspatil.permissionFlow.utils.launch
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.designsystem.common.ErrorItem
import ru.serg.designsystem.common.SunLoadingScreen
import ru.serg.designsystem.top_item.PagerTopBar
import ru.serg.main_pager.CommonScreenState
import ru.serg.main_pager.openAppSystemSettings
import ru.serg.main_pager.updated_pager.PagerScreen
import ru.serg.weather_elements.elements.NoCitiesMainScreenItem

@OptIn(
    ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
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
        val appBarState = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(state.nestedScrollConnection),
            topBar = {
                PagerTopBar(
                    pagerState = pagerState,
                    isLoading = isLoading,
                    onLeftIconClick = remember {
                        navigateToChooseCity
                    },
                    onRightIconClick = remember {
                        navigateToSettings
                    },
                    appBarState = appBarState
                )
            }
        ) { padding ->

            val context = LocalContext.current

            AnimatedVisibility(
                visible = screenState is CommonScreenState.Empty,
                enter = fadeIn(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(
                    animationSpec = tween(300)
                )
            ) {

                NoCitiesMainScreenItem(
                    onSearchClick = remember {
                        navigateToChooseCity
                    },
                    onRequestPermissionClick = remember {
                        {
                            permissionLauncher.launch(
                                ACCESS_COARSE_LOCATION,
                                ACCESS_FINE_LOCATION
                            )
                        }
                    },
                    goToSettings = remember {
                        { context.openAppSystemSettings() }
                    }
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

            AnimatedVisibility(
                visible = screenState is CommonScreenState.Success,
                enter = fadeIn(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(
                    animationSpec = tween(300)
                )
            ) {

                HorizontalPager(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .nestedScroll(appBarState.nestedScrollConnection),
                    state = pagerState,
                    userScrollEnabled = true,
                    reverseLayout = false,
                    pageContent = {
                        PagerScreen(
                            weatherItem = (screenState as CommonScreenState.Success).updatedWeatherList[it],
                            modifier = Modifier
                        )
                    }
                )
            }

            AnimatedVisibility(
                visible = screenState is CommonScreenState.Error,
                enter = fadeIn(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(
                    animationSpec = tween(300)
                )
            ) {
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

