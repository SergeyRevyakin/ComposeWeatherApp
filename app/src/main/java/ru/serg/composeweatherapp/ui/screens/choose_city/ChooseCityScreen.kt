package ru.serg.composeweatherapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.serg.composeweatherapp.ui.elements.CityRow
import ru.serg.composeweatherapp.ui.screens.choose_city.ChooseCityViewModel
import ru.serg.composeweatherapp.ui.elements.SearchTextField
import ru.serg.composeweatherapp.ui.theme.headerStyle

@Composable
fun ChooseCityScreen(
    viewModel: ChooseCityViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        state = rememberLazyListState()
    ) {
        item {
            Text(
                text = "Enter city name",
                style = MaterialTheme.typography.headerStyle,
                modifier = Modifier
                    .padding(vertical = 24.dp)
            )
        }

        item {
            SearchTextField(
                onValueChange = {viewModel.onTextChanged(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                )
        }

//        if (viewModel.screenState.isLoading){
//            item {
//                CircularProgressIndicator(
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        } else if (viewModel.screenState.data.isEmpty()){
//            item {
//                Text(text = "No results",
//                modifier = Modifier.fillMaxSize())
//            } else {
                items(viewModel.screenState.data) {
                    CityRow(cityItem = it)
                }
//            }
//        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChooseCityPreview() {
    ChooseCityScreen()
}