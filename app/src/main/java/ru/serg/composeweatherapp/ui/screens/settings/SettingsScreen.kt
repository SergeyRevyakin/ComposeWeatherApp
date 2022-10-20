package ru.serg.composeweatherapp.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.composeweatherapp.ui.elements.MenuRowWithRadioButton
import ru.serg.composeweatherapp.ui.elements.TopItem

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopItem(
            header = "Settings",
            leftIconImageVector = Icons.Rounded.ArrowBack,
            rightIconImageVector = null,
            onLeftIconClick = { navController.navigateUp() },
            onRightIconClick = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        MenuRowWithRadioButton(
            optionName = "Use dark mode",
            modifier = Modifier,
            buttonState = viewModel.isDarkModeEnabled,
            onSwitchClick = viewModel::onScreenModeChanged
        )

        MenuRowWithRadioButton(
            optionName = "Update weather in background",
            descriptionText = "Allow map get data about weather in background. It will consume some network traffic",
            modifier = Modifier,
            buttonState = viewModel.isBackgroundFetchWeatherEnabled,
            onSwitchClick = viewModel::onBackgroundFetchChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingsScreen()
}
