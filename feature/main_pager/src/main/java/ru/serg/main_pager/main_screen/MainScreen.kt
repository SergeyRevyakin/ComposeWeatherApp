package ru.serg.main_pager.main_screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
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
import ru.serg.designsystem.top_item.ErrorTopBarItem
import ru.serg.designsystem.top_item.PagerTopBar
import ru.serg.main_pager.PagerScreenError
import ru.serg.main_pager.openAppSystemSettings
import ru.serg.main_pager.updated_pager.PagerScreen
import ru.serg.weather_elements.elements.NoCitiesMainScreenItem

@OptIn(
    ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToChooseCity: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val permissionLauncher = rememberPermissionFlowRequestLauncher()
    val screenState by viewModel.pagerScreenState.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        screenState.weatherList.size
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setPageNumber(pagerState.currentPage)
    }

    val pullToRefreshState = rememberPullToRefreshState()


    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            viewModel.refresh()
        }
    }

    LaunchedEffect(screenState.isLoading) {
        pullToRefreshState.endRefresh()
    }

    Box(Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        val appBarState = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection),
            topBar = {
                PagerTopBar(
                    pagerState = pagerState,
                    isLoading = screenState.isLoading,
                    onLeftIconClick = remember {
                        navigateToChooseCity
                    },
                    onRightIconClick = remember {
                        navigateToSettings
                    },
                    appBarState = appBarState,
                ) {
                    AnimatedVisibility(
                        visible = screenState.error is PagerScreenError,
                        enter = expandVertically(
                            animationSpec = tween(300)
                        ) + slideInVertically(
                            animationSpec = tween(500),
                            initialOffsetY = { -it }
                        ),
                        exit = shrinkVertically(
                            animationSpec = tween(500, delayMillis = 3000)
                        )
                    ) {
                        (screenState.error as? PagerScreenError.NetworkError)?.throwable?.let {
                            ErrorTopBarItem(
                                it
                            )
                        }
                    }
                }
            }
        ) { padding ->

            val context = LocalContext.current

            AnimatedVisibility(
                visible = screenState.error != null && screenState.weatherList.isEmpty(),
                enter = fadeIn(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(
                    animationSpec = tween(300)
                )
            ) {
                pullToRefreshState.endRefresh()
                ErrorItem(onRefreshClick = { viewModel.initCitiesWeatherFlow() })
            }

            AnimatedVisibility(
                visible = screenState.weatherList.isEmpty() && !screenState.isStartUp && !screenState.isLoading,
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
                    },
                    hasWelcomeBottomSheet = screenState.hasWelcomeDialog
                )

                viewModel.turnOffDialog()
            }

            AnimatedVisibility(
                visible = screenState.isStartUp || (screenState.weatherList.isEmpty() && screenState.isLoading),
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
                visible = screenState.weatherList.isNotEmpty(),
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
                        val weatherItem = screenState.weatherList[it]
                        if (weatherItem.dailyWeatherList.isEmpty() || weatherItem.hourlyWeatherList.isEmpty()) {
                            ErrorItem(onRefreshClick = { viewModel.initCitiesWeatherFlow() })
                        } else {
                            PagerScreen(
                                weatherItem = weatherItem,
                                modifier = Modifier
                            )
                        }
                    }
                )
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colorScheme.primary,
        )
    }
}

