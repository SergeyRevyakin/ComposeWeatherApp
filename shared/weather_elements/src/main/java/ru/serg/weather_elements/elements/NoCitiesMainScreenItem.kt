package ru.serg.weather_elements.elements

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.serg.designsystem.common.CardButton
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.strings.R.string

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoCitiesMainScreenItem(
    onSearchClick: (() -> Unit),
    onRequestPermissionClick: (() -> Unit),
    goToSettings: (() -> Unit),
) {

    val sheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Expanded,
//        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
//        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()


    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
//
//    ModalBottomSheet(
//        sheetState = sheetState,
//        sheetContent = {
//            WelcomeBottomSheet(
//                { coroutineScope.launch { sheetState.hide() } },
//                onSearchClick,
//                onRequestPermissionClick
//            )
//        },
//        modifier = Modifier.fillMaxSize()
//            .clip(RoundedCornerShape(24.dp)),
//        sheetShape = ,
//        sheetElevation = 10.dp,
//        sheetBackgroundColor = Color.Transparent,
//        scrimColor = Color.Transparent,
//    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp, bottom = 24.dp)
//                .animatedBlur(sheetState.targetValue == ModalBottomSheetValue.Expanded),
        ) {
            Text(
                text = stringResource(id = string.no_weather_data_available),
                style = headerStyle,
                modifier = Modifier
                    .headerModifier()
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
            )

            CardButton(
                buttonText = stringResource(id = string.please_allow_us_to_get_location_access),
                image = Icons.Rounded.LocationSearching
            ) {
                onRequestPermissionClick()
            }

            Text(
                text = stringResource(id = string.click_here_to_change_it_in_settings),
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(bottom = 32.dp)
                    .clickable {
                        goToSettings()
                    },
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            CardButton(
                buttonText = stringResource(id = string.or_find_city_manually),
                image = Icons.Rounded.Search
            ) {
                onSearchClick()
            }

//        }
    }
}

@Preview
@Composable
fun PreviewNoCitiesMainScreenItem() {
    NoCitiesMainScreenItem({}, {}, {})
}