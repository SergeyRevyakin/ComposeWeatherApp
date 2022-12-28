package ru.serg.composeweatherapp.ui.screens.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.service.FetchWeatherService
import ru.serg.composeweatherapp.ui.elements.settings.HourSliderItem
import ru.serg.composeweatherapp.ui.elements.settings.MenuLocationRowWithIcon
import ru.serg.composeweatherapp.ui.elements.settings.MenuRowWithRadioButton
import ru.serg.composeweatherapp.ui.elements.top_item.TopItem
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.Ext.openAppSystemSettings
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val intent = Intent(context, FetchWeatherService::class.java)

    //TODO Rework alarm manager status
    val isAlarmOn = remember {
        mutableStateOf(
            PendingIntent.getForegroundService(
                context,
                Constants.ALARM_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_NO_CREATE
            ) != null
        )
    }

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

        MenuRowWithRadioButton(
            optionName = "Fetch weather every morning",
            descriptionText = "Allow app get weather data every morning and show in notification. It will consume some network traffic",
            modifier = Modifier,
            buttonState = isAlarmOn,
            onSwitchClick = { setAlarm(context.applicationContext) }
        )
    }
}

fun setAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, FetchWeatherService::class.java)
    val pendingIntent = PendingIntent.getForegroundService(
        context,
        Constants.ALARM_REQUEST_CODE,
        intent,
        PendingIntent.FLAG_MUTABLE
    )

    val tommorrowMorning =
        LocalDateTime.now().plusDays(1)
            .withHour(8)
            .withMinute(0)
            .atZone(ZoneId.systemDefault())
            .toEpochSecond()


    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        getTimeMillis(),
        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
        pendingIntent
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingsScreen()
}
