@file:OptIn(ExperimentalMaterial3Api::class)

package ru.serg.settings_feature.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.serg.designsystem.simple_items.MenuCommonButton
import ru.serg.designsystem.simple_items.MenuRowWithRadioButton
import ru.serg.designsystem.simple_items.MenuSettingsRowWithIcon
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.designsystem.top_item.TopBar
import ru.serg.designsystem.top_item.TopBarHolder
import ru.serg.model.enums.Units
import ru.serg.navigation.WidgetSettingsScreen
import ru.serg.settings_feature.Constants
import ru.serg.settings_feature.elements.CollapsingContainer
import ru.serg.settings_feature.elements.HourSliderItem
import ru.serg.settings_feature.elements.RadioButtonGroup
import ru.serg.settings_feature.getLocationDescriptionText
import ru.serg.settings_feature.getLocationHeaderText
import ru.serg.settings_feature.getLocationIcon
import ru.serg.settings_feature.getNotificationDescription
import ru.serg.settings_feature.getNotificationHeader
import ru.serg.settings_feature.getNotificationIcon
import ru.serg.settings_feature.isTiramisuOrAbove
import ru.serg.settings_feature.openAppSystemSettings
import ru.serg.strings.R.string

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
) {
    val viewModel: SettingViewModel = hiltViewModel()
    val context = LocalContext.current

    val appBarState = TopAppBarDefaults.pinnedScrollBehavior()
    val header = stringResource(id = string.settings)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            TopBar(
                holder = remember {
                    TopBarHolder(
                        header = header,
                        leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                        rightIconImageVector = null,
                        onLeftIconClick =
                        { navController.navigateUp() },
                        onRightIconClick = null,
                        appBarState = appBarState
                    )
                },
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .nestedScroll(appBarState.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            MenuRowWithRadioButton(
                optionName = stringResource(id = string.use_dark_mode),
                modifier = Modifier,
                buttonState = viewModel.isDarkModeEnabled.collectAsState().value,
                onSwitchClick = viewModel::onScreenModeChanged
            )

            val isNotificationEnabled = viewModel.isNotificationEnabled.collectAsState().value
            val notificationIcon = getNotificationIcon(isNotificationEnabled)
            val notificationHeader = getNotificationHeader(isNotificationEnabled)
            val notificationDescription = getNotificationDescription(isNotificationEnabled)

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
                buttonState = viewModel.isBackgroundFetchWeatherEnabled.collectAsState().value,
                onSwitchClick = viewModel::onBackgroundFetchChanged
            )

            CollapsingContainer(isVisible = viewModel.isBackgroundFetchWeatherEnabled.collectAsState()) {

                MenuRowWithRadioButton(
                    optionName = stringResource(id = string.show_notifications_when_updated),
                    modifier = Modifier,
                    buttonState = viewModel.isUserNotificationTurnOn.collectAsState().value,
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
                navController.navigate(WidgetSettingsScreen)
            }

            val isLocationEnabled = viewModel.isLocationEnabled.collectAsState().value
            val locationIcon = getLocationIcon(isLocationEnabled)
            val locationHeaderText = getLocationHeaderText(isLocationEnabled)
            val locationDescriptionText = getLocationDescriptionText(isLocationEnabled)

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
                text = stringResource(
                    id = string.settings_current_app_verions,
                    versionName.toString()
                ),
                style = settingsSubText,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
            )
        }
    }
}
