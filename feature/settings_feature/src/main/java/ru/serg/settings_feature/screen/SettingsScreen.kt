package ru.serg.settings_feature.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocationOff
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsOff
import androidx.compose.material3.Text
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
import ru.serg.common.ScreenNames
import ru.serg.designsystem.simple_items.MenuCommonButton
import ru.serg.designsystem.simple_items.MenuRowWithRadioButton
import ru.serg.designsystem.simple_items.MenuSettingsRowWithIcon
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.designsystem.top_item.TopItem
import ru.serg.model.enums.Units
import ru.serg.settings_feature.Constants
import ru.serg.settings_feature.elements.CollapsingContainer
import ru.serg.settings_feature.elements.HourSliderItem
import ru.serg.settings_feature.elements.RadioButtonGroup
import ru.serg.settings_feature.isTiramisuOrAbove
import ru.serg.settings_feature.openAppSystemSettings
import ru.serg.strings.R.string

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
            header = stringResource(id = string.settings),
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            rightIconImageVector = null,
            onLeftIconClick = { navController.navigateUp() },
            onRightIconClick = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        MenuRowWithRadioButton(
            optionName = stringResource(id = string.use_dark_mode),
            modifier = Modifier,
            buttonState = viewModel.isDarkModeEnabled.collectAsState(),
            onSwitchClick = viewModel::onScreenModeChanged
        )

        val notificationIcon =
            if (viewModel.isNotificationEnabled.collectAsState().value) Icons.Rounded.Notifications else Icons.Rounded.NotificationsOff

        val notificationHeader = if (viewModel.isNotificationEnabled.collectAsState().value)
            stringResource(id = string.app_can_send_you_notifications)
        else stringResource(id = string.app_can_not_send_you_notifications)

        val notificationDescription = if (viewModel.isNotificationEnabled.collectAsState().value)
            stringResource(id = string.tap_to_turn_it_on)
        else stringResource(id = string.tap_to_turn_it_off)

        if (isTiramisuOrAbove()) {
            MenuSettingsRowWithIcon(
                onClick = { context.openAppSystemSettings() },
                iconImageVector = notificationIcon,
                headerText = notificationHeader,
                descriptionText = notificationDescription

            )
        }

        MenuRowWithRadioButton(
            optionName = stringResource(id = string.update_weather_in_background),
            descriptionText = stringResource(id = string.allow_get_updates_consumes_traffic),
            modifier = Modifier,
            buttonState = viewModel.isBackgroundFetchWeatherEnabled.collectAsState(),
            onSwitchClick = viewModel::onBackgroundFetchChanged
        )

        CollapsingContainer(isVisible = viewModel.isBackgroundFetchWeatherEnabled.collectAsState()) {

            MenuRowWithRadioButton(
                optionName = stringResource(id = string.show_notifications_when_updated),
                modifier = Modifier,
                buttonState = viewModel.isUserNotificationTurnOn.collectAsState(),
                onSwitchClick = viewModel::saveUserNotifications
            )

            HourSliderItem(
                hours = Constants.HOUR_FREQUENCY_LIST,
                stateValue = viewModel.fetchFrequencyValue.collectAsState() as MutableState<Float>,
                onValueChanged = viewModel::onFrequencyChanged
            )
        }

        MenuCommonButton(
            headerText = stringResource(id = string.show_widget_settings)
        ) {
            navController.navigate(ScreenNames.WIDGET_SETTINGS_SCREEN)
        }

        val locationIcon =
            if (viewModel.isLocationEnabled.collectAsState().value) Icons.Rounded.LocationOn else Icons.Rounded.LocationOff

        val locationHeaderText = if (viewModel.isLocationEnabled.collectAsState().value)
            stringResource(id = string.location_is_on)
        else stringResource(id = string.location_is_off)

        val locationDescriptionText = if (viewModel.isLocationEnabled.collectAsState().value)
            stringResource(id = string.tap_to_turn_it_on)
        else stringResource(id = string.tap_to_turn_it_off)

        MenuSettingsRowWithIcon(
            onClick = { context.openAppSystemSettings() },
            iconImageVector = locationIcon,
            headerText = locationHeaderText,
            descriptionText = locationDescriptionText
        )

        RadioButtonGroup(
            header = stringResource(id = string.measurement_units),
            nameList = Units.entries.map { stringResource(id = it.title) },
            descriptionList = Units.entries.map { stringResource(id = it.description) },
            selectedPosition = viewModel.measurementUnits
        ) {
            viewModel.onUnitsChanged(it)
        }

        val versionName = LocalContext.current.packageManager.getPackageInfo(
            LocalContext.current.packageName,
            0
        ).versionName

        Text(
            text = stringResource(id = string.settings_current_app_verions, versionName),
            style = settingsSubText,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingsScreen()
}
