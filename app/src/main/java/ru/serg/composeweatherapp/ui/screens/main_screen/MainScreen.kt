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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
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

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopItem(
            header = "Current weather in",
            leftIconImageVector = Icons.Rounded.Search,
            rightIconImageVector = Icons.Rounded.Settings,
            onLeftIconClick = navigateToChooseCity,
            onRightIconClick = navigateToSettings
        )

        val context = LocalContext.current

        AnimatedVisibility(visible = viewModel.citiesList.value.isEmpty()) {

            val expRationale = multiplePermissionState.permissions.map {
                it.status.shouldShowRationale
            }.contains(true)

            NoCitiesMainScreenItem(
                onSearchClick = navigateToChooseCity,
                onRequestPermissionClick = { multiplePermissionState.launchMultiplePermissionRequest() },
                goToSettings = { context.openAppSystemSettings() }

            )
        }

        if (multiplePermissionState.permissions != multiplePermissionState.revokedPermissions) {
            viewModel.fillCitiesList(true)
        }

        AnimatedVisibility(visible = viewModel.citiesList.value.isNotEmpty()) {

            HorizontalPager(
                count = viewModel.citiesList.value.size,
                state = rememberPagerState(),
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                PagerScreen(
                    cityItem = viewModel.citiesList.value[page],
                    currentPage == page //To prevent preload of next page
                )
            }
        }
    }

}

