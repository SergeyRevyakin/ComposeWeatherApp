package ru.serg.composeweatherapp.ui.screens.main_screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ru.serg.composeweatherapp.ui.elements.common.NoCitiesMainScreenItem
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.ui.screens.pager.PagerScreen
import ru.serg.composeweatherapp.utils.Ext.openAppSystemSettings


@OptIn(ExperimentalPagerApi::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToChooseCity: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        )
    )

    val citiesList by viewModel.citiesList.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopItem(
            header = "Current weather in",
            leftIconImageVector = Icons.Rounded.Search,
            rightIconImageVector = Icons.Rounded.Settings,
            onLeftIconClick = navigateToChooseCity,
            onRightIconClick = navigateToSettings,
            isLoading = viewModel.isLoading.value
        )

        val context = LocalContext.current

        AnimatedVisibility(visible = citiesList.isEmpty() && !viewModel.isLoading.value) {

            NoCitiesMainScreenItem(
                onSearchClick = navigateToChooseCity,
                onRequestPermissionClick = { multiplePermissionState.launchMultiplePermissionRequest() },
                goToSettings = { context.openAppSystemSettings() }

            )
        }
//
//        LaunchedEffect(multiplePermissionState.permissions != multiplePermissionState.revokedPermissions) {
//            viewModel.fillCitiesList(true)
//        }
//        LaunchedEffect(multiplePermissionState.permissions == multiplePermissionState.revokedPermissions) {
//            viewModel.fillCitiesList(false)
//        }

        AnimatedVisibility(visible = citiesList.isNotEmpty()) {

            HorizontalPager(
                count = viewModel.citiesList.collectAsState().value.size,
                state = rememberPagerState(),
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                PagerScreen(
                    cityItem = citiesList[page],
                    currentPage == page //To prevent preload of next page
                )
            }
        }
    }

}

