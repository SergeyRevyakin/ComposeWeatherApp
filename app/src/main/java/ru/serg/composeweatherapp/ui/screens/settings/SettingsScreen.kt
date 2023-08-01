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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.elements.settings.HourSliderItem
import ru.serg.composeweatherapp.ui.elements.settings.RadioButtonGroup
import ru.serg.composeweatherapp.ui.elements.simple_items.MenuRowWithRadioButton
import ru.serg.composeweatherapp.ui.elements.simple_items.MenuSettingsRowWithIcon
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.enums.Units
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
            header = stringResource(id = R.string.settings),
            leftIconImageVector = Icons.Rounded.ArrowBack,
            rightIconImageVector = null,
            onLeftIconClick = { navController.navigateUp() },
            onRightIconClick = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        MenuRowWithRadioButton(
            optionName = stringResource(id = R.string.use_dark_mode),
            modifier = Modifier,
            buttonState = viewModel.isDarkModeEnabled.collectAsState(),
            onSwitchClick = viewModel::onScreenModeChanged
        )

        if (isTiramisuOrAbove()) {
            MenuSettingsRowWithIcon(
                onClick = { context.openAppSystemSettings() },
                iconImageVector = if (viewModel.isNotificationEnabled.collectAsState().value) Icons.Rounded.Notifications else Icons.Rounded.NotificationsOff,
                headerText = if (viewModel.isNotificationEnabled.collectAsState().value) stringResource(
                    id = R.string.app_can_send_you_notifications
                )
                else stringResource(
                    id = R.string.app_can_not_send_you_notifications
                ),
                descriptionText = if (viewModel.isNotificationEnabled.collectAsState().value) stringResource(
                    id = R.string.tap_to_turn_it_on
                )
                else stringResource(
                    id = R.string.tap_to_turn_it_off
                )

            )
        }

        MenuRowWithRadioButton(
            optionName = stringResource(id = R.string.update_weather_in_background),
            descriptionText = stringResource(id = R.string.allow_get_updates_consumes_traffic),
            modifier = Modifier,
            buttonState = viewModel.isBackgroundFetchWeatherEnabled.collectAsState(),
            onSwitchClick = viewModel::onBackgroundFetchChanged
        )

        HourSliderItem(
            isVisible = viewModel.isBackgroundFetchWeatherEnabled.collectAsState().value,
            hours = Constants.HOUR_FREQUENCY_LIST,
            stateValue = viewModel.fetchFrequencyValue.collectAsState() as MutableState<Float>,
            onValueChanged = viewModel::onFrequencyChanged
        )

        MenuSettingsRowWithIcon(
            onClick = { context.openAppSystemSettings() },
            iconImageVector = if (viewModel.isLocationEnabled.collectAsState().value) Icons.Rounded.LocationOn else Icons.Rounded.LocationOff,
            headerText = if (viewModel.isLocationEnabled.collectAsState().value) stringResource(id = R.string.location_is_on)
            else stringResource(id = R.string.location_is_off),
            descriptionText = if (viewModel.isLocationEnabled.collectAsState().value) stringResource(
                id = R.string.tap_to_turn_it_on
            )
            else stringResource(id = R.string.tap_to_turn_it_off)

        )

        MenuRowWithRadioButton(
            optionName = stringResource(id = R.string.fetch_weather_every_morning),
            descriptionText = stringResource(id = R.string.allow_get_updates_consumes_traffic),
            modifier = Modifier,
            buttonState = viewModel.alarmState.collectAsState(),
            onSwitchClick = { viewModel.onAlarmChanged() }
        )

        RadioButtonGroup(
            header = stringResource(id = R.string.measurement_units),
            nameList = Units.values().map { stringResource(id = it.title) },
            descriptionList = Units.values().map { stringResource(id = it.description) },
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
