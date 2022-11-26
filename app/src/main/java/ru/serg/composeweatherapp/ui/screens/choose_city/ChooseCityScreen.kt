package ru.serg.composeweatherapp.ui.screens.choose_city

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.serg.composeweatherapp.ui.elements.city_search.CityRow
import ru.serg.composeweatherapp.ui.elements.city_search.CitySearchItem
import ru.serg.composeweatherapp.ui.elements.city_search.SearchTextField
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.ScreenNames

@ExperimentalFoundationApi
@FlowPreview
@Composable
fun ChooseCityScreen(
    modifier: Modifier = Modifier,
    viewModel: ChooseCityViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

    val favouriteCities by viewModel.favouriteCitiesList.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState()),
    ) {
        TopItem(
            header = "Choose the city",
            leftIconImageVector = Icons.Rounded.ArrowBack,
            rightIconImageVector = null,
            onLeftIconClick = { navController.navigateUp() },
            onRightIconClick = null,
            isLoading = viewModel.screenState.isLoading
        )

        SearchTextField(
            value = viewModel.inputSharedFlow.collectAsState(initial = Constants.EMPTY_STRING).value,
            onValueChange = viewModel::onSharedFlowText,
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )

//        LazyHorizontalStaggeredGrid(
//            rows = StaggeredGridCells.Fixed(2),
//            state = rememberLazyStaggeredGridState(),
//            contentPadding = PaddingValues(16.dp),
//            modifier = Modifier.height(128.dp)
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//        ) {
//
//            items(viewModel.searchHistoryItems) {
//                CitySearchItem(
//                    cityItem = it,
//                    onDelete = viewModel::onDeleteClick
//                )
//            }
//        }
        AnimatedVisibility(visible = favouriteCities.isNotEmpty()) {

            Column() {
                Text(
                    text = "Favourite cities",
                    style = MaterialTheme.typography.headerStyle,
                    modifier = Modifier
                        .headerModifier()
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    state = rememberLazyListState()
                ) {
                    items(favouriteCities) {
                        CitySearchItem(
                            cityItem = it,
                            onDelete = viewModel::onDeleteClick,
                            onItemClick = { cityItem ->
                                navController.navigate(
                                    "${ScreenNames.CITY_WEATHER_SCREEN}/${
                                        Json.encodeToString(
                                            cityItem
                                        )
                                    }"
                                )
                            }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = (!viewModel.screenState.isLoading && !viewModel.screenState.message.isNullOrBlank()),
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewModel.screenState.message.orEmpty(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 64.dp)
                )
            }
        }

        when {
            (viewModel.screenState.isLoading) -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { }
            }
            (viewModel.screenState.data.isNotEmpty()) -> {
                Text(
                    text = "Are you looking for one of this?",
                    style = MaterialTheme.typography.headerStyle,
                    modifier = Modifier
                        .headerModifier()
                )
                Column(modifier = modifier.padding(horizontal = 16.dp)) {
                    viewModel.screenState.data.forEach { cityItem ->
                        CityRow(
                            cityItem = cityItem,
                            onItemClick = {
                                navController.navigate(
                                    "${ScreenNames.CITY_WEATHER_SCREEN}/${Json.encodeToString(it)}"
                                )
                            },
                            onAddClick = viewModel::onCityClicked
                        )
                    }
                }
            }
        }
    }
}

