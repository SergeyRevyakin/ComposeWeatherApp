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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.serg.composeweatherapp.ui.elements.*
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

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        TopItem(
            header = "Choose the city",
            leftIconImageVector = Icons.Rounded.ArrowBack,
            rightIconImageVector = null,
            onLeftIconClick = { navController.navigateUp() },
            onRightIconClick = null
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
                    items(viewModel.screenState.data) { cityItem ->
                        CityRow(
                            cityItem = cityItem,
                            {
                                navController.navigate(
                                    "${ScreenNames.CITY_WEATHER_SCREEN}/${Json.encodeToString(it)}"
                                )
                            },
                            viewModel::onCityClicked
                        )
                    }
                }
            }
            (viewModel.screenState.message == "Error") -> {
                ErrorItem(errorText = "Error")
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