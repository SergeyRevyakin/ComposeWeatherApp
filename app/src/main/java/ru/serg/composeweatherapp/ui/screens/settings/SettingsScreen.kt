package ru.serg.composeweatherapp.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocationOff
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.composeweatherapp.ui.elements.settings.HourSliderItem
import ru.serg.composeweatherapp.ui.elements.settings.MenuRowWithRadioButton
import ru.serg.composeweatherapp.ui.elements.settings.MenuSettingsRowWithIcon
import ru.serg.composeweatherapp.ui.elements.settings.RadioButtonGroup
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.isTiramisuOrAbove
import ru.serg.composeweatherapp.utils.openAppSystemSettings

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingViewModel = hiltViewModel()
) {
    val scrollableState = rememberScrollState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollableState, enabled = true)
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
            buttonState = viewModel.isDarkModeEnabled.collectAsState(),
            onSwitchClick = viewModel::onScreenModeChanged
        )

        if (isTiramisuOrAbove()) {
            MenuSettingsRowWithIcon(
                onClick = { context.openAppSystemSettings() },
                iconImageVector = if (viewModel.isNotificationEnabled.collectAsState().value) Icons.Rounded.Notifications else Icons.Rounded.NotificationsOff,
                headerText = if (viewModel.isNotificationEnabled.collectAsState().value) "Now app can send you notifications"
                else "Now app can't send you notifications",
                descriptionText = if (viewModel.isNotificationEnabled.collectAsState().value) "Tap if you want to turn it off"
                else "Tap if you want to turn it on"

            )
        }

        MenuRowWithRadioButton(
            optionName = "Update weather in background",
            descriptionText = "Allow map get data about weather in background. It will consume some network traffic",
            modifier = Modifier,
            buttonState = viewModel.isBackgroundFetchWeatherEnabled.collectAsState(),
            onSwitchClick = viewModel::onBackgroundFetchChanged
        )

        HourSliderItem(
            isVisible = viewModel.isBackgroundFetchWeatherEnabled.collectAsState().value,
            hours = Constants.HOUR_FREQUENCY_LIST,
            value = viewModel.fetchFrequencyValue.collectAsState().value,
            onValueChanged = viewModel::onFrequencyChanged
        )

        MenuSettingsRowWithIcon(
            onClick = { context.openAppSystemSettings() },
            iconImageVector = if (viewModel.isLocationEnabled.collectAsState().value) Icons.Rounded.LocationOn else Icons.Rounded.LocationOff,
            headerText = if (viewModel.isLocationEnabled.collectAsState().value) "Right now application has access to your device location"
            else "Right now application don't have access to your device location",
            descriptionText = if (viewModel.isLocationEnabled.collectAsState().value) "Tap if you want to turn it off"
            else "Tap if you want to turn it on"

        )

        MenuRowWithRadioButton(
            optionName = "Fetch weather every morning",
            descriptionText = "Allow app get weather data every morning and show in notification. It will consume some network traffic",
            modifier = Modifier,
            buttonState = viewModel.alarmState.collectAsState(),
            onSwitchClick = { viewModel.onAlarmChanged() }
        )

        RadioButtonGroup(
            header = "Measurement units",
            nameList = Constants.DataStore.Units.values().map { it.title },
            descriptionList = Constants.DataStore.Units.values().map { it.description },
            selectedPosition = viewModel.measurementUnits
        ) {
            viewModel.onUnitsChanged(it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingsScreen()
}
