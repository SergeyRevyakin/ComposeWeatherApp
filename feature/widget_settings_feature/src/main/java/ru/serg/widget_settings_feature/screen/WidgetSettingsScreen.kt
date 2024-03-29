package ru.serg.widget_settings_feature.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.designsystem.simple_items.MenuRowWithRadioButton
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.headerModifier
import ru.serg.designsystem.theme.headerStyle
import ru.serg.designsystem.top_item.TopItem
import ru.serg.strings.R
import ru.serg.widget_settings_feature.items.MenuCommonColorButton
import ru.serg.widget_settings_feature.items.SliderWithButtons
import ru.serg.widget_settings_feature.items.WidgetPreviewItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WidgetSettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: WidgetSettingsViewModel = hiltViewModel()
) {
    val openColorPickDialog = remember { mutableStateOf(false) }

    val colorState by viewModel.widgetColorFlow.collectAsState()
    val bigFontSize by viewModel.widgetBigFontFlow.collectAsState()
    val smallFontSize by viewModel.widgetSmallFontFlow.collectAsState()
    val bottomPadding by viewModel.widgetBottomPadding.collectAsState()
    val iconSize by viewModel.widgetIconSize.collectAsState()
    val isWidgetSystemDataShown = viewModel.isWidgetSystemDataShown.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(state = scrollableState, enabled = true)
    ) {
        item {
            TopItem(
                header = stringResource(id = R.string.widget_settings),
                leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                rightIconImageVector = null,
                onLeftIconClick = { navController.navigateUp() },
                onRightIconClick = null
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.widget_preview),
                style = headerStyle,
                modifier = Modifier.headerModifier()
            )
        }

        stickyHeader {
            WidgetPreviewItem(
                color = colorState,
                bigFont = bigFontSize.toInt(),
                smallFont = smallFontSize.toInt(),
                bottomPadding = bottomPadding.toInt(),
                isSystemDataShown = isWidgetSystemDataShown.value,
                iconSize = iconSize.toInt(),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }

        item {
            MenuCommonColorButton(
                headerText = stringResource(id = R.string.widget_color),
                color = colorState
            ) {
                openColorPickDialog.value = true
            }
        }

        if (openColorPickDialog.value) {
            item {
                ColorPickerDialogScreen(
                    {
                        openColorPickDialog.value = false
                    },
                    color = colorState,
                    onColor = {
                        openColorPickDialog.value = false
                        viewModel.saveWidgetColor(it)
                    },
                )
            }
        }

        item {
            Text(
                text = stringResource(id = R.string.widget_big_font_size),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp, top = 36.dp)
                    .padding(horizontal = 24.dp)
            )
        }

        item {
            SliderWithButtons(
                value = bigFontSize,
                valueRange = 22f..50f,
                onValueChange = { viewModel.saveWidgetBigFont(it) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.widget_small_font_size),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp, top = 36.dp)
                    .padding(horizontal = 24.dp)
            )
        }

        item {
            SliderWithButtons(
                value = smallFontSize,
                valueRange = 12f..24f,
                onValueChange = { viewModel.saveWidgetSmallFont(it) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.widget_bottom_padding),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp, top = 36.dp)
                    .padding(horizontal = 24.dp)
            )
        }

        item {
            SliderWithButtons(
                value = bottomPadding,
                valueRange = 0f..12f,
                onValueChange = { viewModel.saveWidgetBottomPadding(it) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.widget_icon_size),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp, top = 36.dp)
                    .padding(horizontal = 24.dp)
            )
        }

        item {
            SliderWithButtons(
                value = iconSize,
                valueRange = 24f..64f,
                onValueChange = { viewModel.saveWidgetIconSize(it) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        item {
            MenuRowWithRadioButton(
                optionName = stringResource(id = R.string.widget_system_data_label),
                descriptionText = stringResource(id = R.string.widget_system_data_desc),
                buttonState = isWidgetSystemDataShown,
                modifier = Modifier.padding(top = 12.dp),
                onSwitchClick = { viewModel.saveWidgetSystemDataShown(it) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWidgetSettingsScreen() {
    ComposeWeatherAppTheme {
        WidgetSettingsScreen()
    }
}