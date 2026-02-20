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
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.shreyaspatil.permissionFlow.utils.launch
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.designsystem.common.ErrorItem
import ru.serg.designsystem.common.SunLoadingScreen
import ru.serg.designsystem.top_item.ErrorTopBarItem
import ru.serg.designsystem.top_item.PagerTopBar
import ru.serg.main_pager.PagerScreenError
import ru.serg.main_pager.mvi.MainScreenIntent
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
    val screenState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        screenState.weatherList.size
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.sendAction(MainScreenIntent.SetPageNumber(pagerState.currentPage))
    }

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(screenState.isLoading) {
        pullToRefreshState.animateToHidden()
    }

    val appBarState = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .consumeWindowInsets(
                WindowInsets.navigationBars
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
                    if (screenState.isNetworkAvailable) {
                        ErrorTopBarItem(
                            (screenState.error as? PagerScreenError.NetworkError)?.throwable
                        )
                    }
                }
            }
        },
    ) { padding ->

//        AnimatedVisibility(
//            visible = screenState.error != null && screenState.weatherList.isEmpty(),
//            enter = fadeIn(
//                animationSpec = tween(300)
//            ),
//            exit = fadeOut(
//                animationSpec = tween(300)
//            )
//        ) {
//            ErrorItem(
//                errorText = screenState.error?.message,
//                onRefreshClick = { viewModel.sendAction(MainScreenIntent.RefreshScreen) })
//        }

        AnimatedVisibility(
            visible = screenState.weatherList.isEmpty()
                    && !screenState.isLoading && screenState.error == null,
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

            LaunchedEffect(Unit) {
                viewModel.sendAction(
                    MainScreenIntent.ShowEmptyCitiesScreen(
                        hasWelcomeDialog = false,
                        isLoading = false
                    )
                )
            }
        }

        AnimatedVisibility(
            visible = screenState.weatherList.isEmpty() && screenState.isLoading
                    && screenState.error == null,
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
                onRefresh = { viewModel.sendAction(MainScreenIntent.RefreshScreen) },
                isRefreshing = false,
                indicator = {
                    Indicator(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 144.dp),
                        isRefreshing = screenState.isLoading,
                        state = pullToRefreshState,
                    )
                }
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .nestedScroll(appBarState.nestedScrollConnection),
                    state = pagerState,
                    userScrollEnabled = true,
                    reverseLayout = false,
                    pageContent = {
                        val weatherItem = screenState.weatherList[it]

                        if ((weatherItem.dailyWeatherList.isEmpty() || weatherItem.hourlyWeatherList.isEmpty()) && screenState.error != null) {
                            ErrorItem(onRefreshClick = { viewModel.sendAction(MainScreenIntent.RefreshScreen) })
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

