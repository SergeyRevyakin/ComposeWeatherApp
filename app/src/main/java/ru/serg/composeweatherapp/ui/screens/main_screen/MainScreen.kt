package ru.serg.composeweatherapp.ui.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.ui.screens.pager.PagerScreen


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToChooseCity: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {

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

