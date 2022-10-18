package ru.serg.composeweatherapp.ui.screens.choose_city

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.serg.composeweatherapp.ui.elements.CityRow
import ru.serg.composeweatherapp.ui.elements.CitySearchItem
import ru.serg.composeweatherapp.ui.elements.SearchTextField
import ru.serg.composeweatherapp.ui.theme.headerStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChooseCityScreen(
    modifier: Modifier = Modifier,
    viewModel: ChooseCityViewModel = hiltViewModel(),
) {
    viewModel.init()


        Column(
            modifier = modifier
                .fillMaxSize(),
//            state = rememberLazyListState()
        ) {
//            item {
                Text(
                    text = "Enter city name",
                    style = MaterialTheme.typography.headerStyle,
                    modifier = Modifier
                        .padding(24.dp)
                )
//            }

//            item {
                SearchTextField(
                    onValueChange = { viewModel.onTextChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                )
//            }

//            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp),
//                    state = rememberLazyGridState()
                ) {
                    items(viewModel.searchHistoryItems) {
                        AnimatedVisibility(visible = viewModel.searchHistoryItems.contains(it),
                        exit = fadeOut(),
                        enter = fadeIn()) {
                            CitySearchItem(cityItem = it, onDelete = viewModel::onDeleteClick)
                        }
//                        CitySearchItem(cityItem = it, onDelete = viewModel::onDeleteClick) //, Modifier.animateItemPlacement()
                    }
                }
//            }

            if (viewModel.screenState.isLoading) {
//                item {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize()
                    )
//                }
            } else if (viewModel.screenState.data.isEmpty()) {
//                item {
                    Text(
                        text = "No results",
                        modifier = Modifier.fillMaxSize()
                    )
//                }
            } else {
                LazyColumn() {
                items(viewModel.screenState.data) {
                    CityRow(cityItem = it, viewModel::onCityClicked)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChooseCityPreview() {
    ChooseCityScreen()
}