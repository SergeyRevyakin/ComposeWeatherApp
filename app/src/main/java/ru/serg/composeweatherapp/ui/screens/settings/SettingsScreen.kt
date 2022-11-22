package ru.serg.composeweatherapp.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.composeweatherapp.ui.elements.settings.HourSliderItem
import ru.serg.composeweatherapp.ui.elements.settings.MenuLocationRowWithIcon
import ru.serg.composeweatherapp.ui.elements.settings.MenuRowWithRadioButton
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.Ext.openAppSystemSettings

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingViewModel = hiltViewModel()
) {

    val context = LocalContext.current

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

        HourSliderItem(
            isVisible = viewModel.isBackgroundFetchWeatherEnabled.value,
            hours = Constants.HOUR_FREQUENCY_LIST,
            value = viewModel.fetchFrequencyValue.value,
            onValueChanged = viewModel::onFrequencyChanged
        )

        MenuLocationRowWithIcon(
            isLocationAvailable = viewModel.isLocationEnabled.value,
            onClick = { context.openAppSystemSettings() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingsScreen()
}
