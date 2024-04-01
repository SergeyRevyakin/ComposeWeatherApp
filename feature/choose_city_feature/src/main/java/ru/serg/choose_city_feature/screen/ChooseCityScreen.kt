package ru.serg.choose_city_feature.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.serg.choose_city_feature.Constants
import ru.serg.choose_city_feature.elements.CityRow
import ru.serg.choose_city_feature.elements.CitySearchItem
import ru.serg.choose_city_feature.elements.SearchTextField
import ru.serg.choose_city_feature.screen.screen_state.Action
import ru.serg.choose_city_feature.screen.screen_state.ScreenError
import ru.serg.common.ScreenNames
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.designsystem.top_item.TopItem
import ru.serg.strings.R.string

@ExperimentalFoundationApi
@FlowPreview
@Composable
fun ChooseCityScreen(
    modifier: Modifier = Modifier,
    viewModel: ChooseCityViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val screenState by viewModel.screenState.collectAsState()
    val favouriteCities = screenState.favoriteCitiesList
    val searchText = screenState.searchText

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState()),
    ) {
        TopItem(
            header = stringResource(id = string.look_for_the_place),
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            rightIconImageVector = null,
            onLeftIconClick = { navController.navigateUp() },
            onRightIconClick = null,
            isLoading = screenState.isLoading
        )

        SearchTextField(
            value = searchText,
            onValueChange = {
                viewModel.handleIntent(Intent.OnTextChanges(it))
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )

        AnimatedVisibility(visible = favouriteCities.isNotEmpty()) {

            Column {
                Text(
                    text = stringResource(id = string.favourite_places),
                    style = headerStyle,
                    modifier = Modifier
                        .headerModifier()
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    state = rememberLazyListState()
                ) {
                    items(
                        favouriteCities.size,
                        key = { it }) { item ->
                        CitySearchItem(
                            cityItem = favouriteCities[item],
                            onDelete = {
                                viewModel.doAction(Action.OnDeleteCityClick(it))
                            },
                            onItemClick = { cityItem ->
                                navController.navigate(
                                    "${ScreenNames.CITY_WEATHER_SCREEN}/${
                                        Json.encodeToString(
                                            cityItem
                                        )
                                    }"
                                )
                            },
                            modifier = Modifier
                                .animateItemPlacement()
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = (!screenState.isLoading && (screenState.screenError != null)),
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val screenErrorText = when (screenState.screenError) {
                    ScreenError.NO_CITIES -> stringResource(id = string.error_no_results_found)
                    ScreenError.NETWORK_ERROR -> stringResource(id = string.error_check_connection_or_try_again_later)
                    else -> Constants.EMPTY_STRING
                }

                Text(
                    text = screenErrorText,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 64.dp)
                )
            }
        }

        if (screenState.foundCitiesList.isNotEmpty()) {
            Text(
                text = stringResource(id = string.are_you_looking_for_one_of_this),
                style = headerStyle,
                modifier = Modifier
                    .headerModifier()
            )
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                screenState.foundCitiesList.forEach { cityItem ->

                    val isFavourite = favouriteCities.any {
                        (it.name == cityItem.name &&
                                it.country == cityItem.country &&
                                it.longitude == cityItem.longitude &&
                                it.latitude == cityItem.latitude)
                    }

                    CityRow(
                        cityItem = cityItem,
                        onItemClick = {
                            navController.navigate(
                                "${ScreenNames.CITY_WEATHER_SCREEN}/${Json.encodeToString(it)}"
                            )
                        },
                        onAddClick = {
                            viewModel.doAction(Action.OnAddCityClick(it))
                        },
                        isAddedToFavorites = isFavourite
                    )
                }
            }
        }
    }
}


