package ru.serg.composeweatherapp.ui.screens.choose_city

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.FlowPreview
import ru.serg.composeweatherapp.ui.elements.CityRow
import ru.serg.composeweatherapp.ui.elements.CitySearchItem
import ru.serg.composeweatherapp.ui.elements.SearchTextField
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.Constants

@ExperimentalFoundationApi
@FlowPreview
@Composable
fun ChooseCityScreen(
    modifier: Modifier = Modifier,
    viewModel: ChooseCityViewModel = hiltViewModel(),
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Enter city name",
            style = MaterialTheme.typography.headerStyle,
            modifier = Modifier
                .padding(top = 36.dp)
                .padding(horizontal = 24.dp)
        )

        SearchTextField(
            value = viewModel.inputSharedFlow.collectAsState(initial = Constants.EMPTY_STRING).value,
            onValueChange = viewModel::onSharedFlowText,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            state = rememberLazyListState()
        ) {
            items(viewModel.searchHistoryItems) {
                CitySearchItem(
                    cityItem = it,
                    onDelete = viewModel::onDeleteClick,
                    modifier = Modifier.animateItemPlacement()
                )
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
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 64.dp)
                    )
                }
            }
            (viewModel.screenState.data.isNotEmpty()) -> {
                LazyColumn(modifier = modifier.padding(24.dp)) {
                    items(viewModel.screenState.data) {
                        CityRow(cityItem = it, viewModel::onCityClicked)
                    }
                }
            }
        }
    }
}


@FlowPreview
@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun ChooseCityPreview() {
    ChooseCityScreen()
}