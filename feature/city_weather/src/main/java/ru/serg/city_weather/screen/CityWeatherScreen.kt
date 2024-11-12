package ru.serg.city_weather.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.designsystem.common.ErrorItem
import ru.serg.designsystem.top_item.TopBar
import ru.serg.designsystem.top_item.TopBarHolder
import ru.serg.strings.R.string
import ru.serg.weather_elements.ScreenState
import ru.serg.weather_elements.weather_screen.CityWeatherContentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityWeatherScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    val viewModel: CityWeatherViewModel = hiltViewModel()
    val screenState by viewModel.uiState.collectAsState()

    val appBarState = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val header = stringResource(id = string.weather_in)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .consumeWindowInsets(
                WindowInsets.navigationBars.only(WindowInsetsSides.Vertical)
            )
            .imePadding(),
        topBar = {
            TopBar(
                holder = remember {
                    TopBarHolder(
                        header = header,
                        leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                        onLeftIconClick = { navController.navigateUp() },
                        appBarState = appBarState
                    )
                },
                isLoading = screenState is ScreenState.Loading
            )

        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .nestedScroll(appBarState.nestedScrollConnection)
        ) {

            AnimatedVisibility(visible = screenState is ScreenState.Error) {
                val error = (screenState as? ScreenState.Error)
                ErrorItem(errorText = error?.message, throwable = error?.throwable) {
                    viewModel.refresh()
                }
            }

            AnimatedVisibility(
                visible = screenState is ScreenState.Success,
                enter = fadeIn(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(
                    animationSpec = tween(300)
                )

            ) {
                val weatherItem = (screenState as? ScreenState.Success)?.weatherItem

                weatherItem?.let {
                    CityWeatherContentItem(
                        weatherItem = weatherItem,
                        modifier = modifier
                    )
                }
            }
        }
    }
}
