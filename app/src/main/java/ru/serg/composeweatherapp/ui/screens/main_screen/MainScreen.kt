package ru.serg.composeweatherapp.ui.screens.main_screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.shreyaspatil.permissionFlow.utils.launch
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.composeweatherapp.ui.elements.PullRefreshBox
import ru.serg.composeweatherapp.ui.elements.common.NoCitiesMainScreenItem
import ru.serg.composeweatherapp.ui.elements.common.SunLoadingScreen
import ru.serg.composeweatherapp.ui.elements.top_item.PagerTopItem
import ru.serg.composeweatherapp.ui.screens.CommonScreenState
import ru.serg.composeweatherapp.ui.screens.updated_pager.UpdatedPagerScreen
import ru.serg.composeweatherapp.utils.openAppSystemSettings

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
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
        viewModel.observableItemNumber.emit(pagerState.currentPage)
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
                animationSpec = tween(500)
            ),
            exit = fadeOut(
                animationSpec = tween(300)
            )
        ) {
            SunLoadingScreen()
        }

        AnimatedVisibility(visible = screenState is CommonScreenState.Success) {

            PullRefreshBox(
                refreshing = viewModel.isLoading.value,
                onRefresh = { viewModel.refresh() }
            ) {
                HorizontalPager(
                    modifier = Modifier.fillMaxWidth(),
                    state = pagerState,
                    userScrollEnabled = true,
                    reverseLayout = false,
                    beyondBoundsPageCount = 1,
                    pageContent = {
                        UpdatedPagerScreen(
                            weatherItem = (screenState as CommonScreenState.Success).updatedWeatherList[it],
                        )
                    }
                )
            }
        }
    }
}

