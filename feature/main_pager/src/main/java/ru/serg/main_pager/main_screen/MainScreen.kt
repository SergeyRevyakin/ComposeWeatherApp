package ru.serg.main_pager.main_screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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


    LaunchedEffect(screenState.isLoading) {
        pullToRefreshState.animateToHidden()
    }
        val appBarState = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = modifier
                .consumeWindowInsets(
                    WindowInsets.navigationBars.only(WindowInsetsSides.Vertical)
                )
                .fillMaxSize(),
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
                        enter = expandVertically(animationSpec = tween(300)),
                        exit = shrinkVertically(animationSpec = tween(300))
                    ) {
                        ErrorTopBarItem(
                            (screenState.error as? PagerScreenError.NetworkError)?.throwable
                        )
                    }
                }
            },
//            contentWindowInsets = WindowInsets.statusBars
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
//                pullToRefreshState.endRefresh()
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
                PullToRefreshBox(
                    state = pullToRefreshState,
                    onRefresh = { viewModel.refresh() },
                    isRefreshing = screenState.isLoading
                ) {
                    HorizontalPager(
                        modifier = Modifier
//                            .safeDrawingPadding()
                            .fillMaxSize()
                            .padding(padding)
                            .nestedScroll(appBarState.nestedScrollConnection),
                        state = pagerState,
                        userScrollEnabled = true,
                        reverseLayout = false,
                        pageContent = {
                            val weatherItem = screenState.weatherList[it]

                            if ((weatherItem.dailyWeatherList.isEmpty() || weatherItem.hourlyWeatherList.isEmpty()) && screenState.error != null) {
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
        }
}

