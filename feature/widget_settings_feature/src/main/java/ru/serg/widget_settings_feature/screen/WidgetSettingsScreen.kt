package ru.serg.widget_settings_feature.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.top_item.TopItem
import ru.serg.strings.R
import ru.serg.widget_settings_feature.items.MenuCommonColorButton

@Composable
fun WidgetSettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: WidgetSettingsViewModel = hiltViewModel()
) {
    val scrollableState = rememberScrollState()

    val openColorPickDialog = remember { mutableStateOf(false) }

    val colorState = viewModel.widgetColorFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollableState, enabled = true)
    ) {
        TopItem(
            header = stringResource(id = R.string.widget_settings),
            leftIconImageVector = Icons.Rounded.ArrowBack,
            rightIconImageVector = null,
            onLeftIconClick = { navController.navigateUp() },
            onRightIconClick = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        MenuCommonColorButton(
            headerText = stringResource(id = R.string.widget_color),
            color = colorState.value
        ) {
            openColorPickDialog.value = true
        }

        if (openColorPickDialog.value) {
            ColorPickerDialogScreen(
                {
                    openColorPickDialog.value = false
                },
                color = colorState.value,
                onColor = {
                    openColorPickDialog.value = false
                    viewModel.saveWidgetColor(it)
                },
            )
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