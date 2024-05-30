package ru.serg.settings_feature

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOff
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.serg.strings.R


fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    })
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun isTiramisuOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

@Composable
fun getNotificationIcon(isNotificationsEnabled: Boolean) =
    if (isNotificationsEnabled) Icons.Rounded.Notifications else Icons.Rounded.NotificationsOff

@Composable
fun getNotificationHeader(isNotificationsEnabled: Boolean) = if (isNotificationsEnabled)
    stringResource(id = R.string.app_can_send_you_notifications)
else stringResource(id = R.string.app_can_not_send_you_notifications)

@Composable
fun getNotificationDescription(isNotificationsEnabled: Boolean) = if (isNotificationsEnabled)
    stringResource(id = R.string.tap_to_turn_it_on)
else stringResource(id = R.string.tap_to_turn_it_off)

@Composable
fun getLocationIcon(isLocationEnabled: Boolean) =
    if (isLocationEnabled) Icons.Rounded.LocationOn else Icons.Rounded.LocationOff

@Composable
fun getLocationHeaderText(isLocationEnabled: Boolean) = if (isLocationEnabled)
    stringResource(id = R.string.location_is_on)
else stringResource(id = R.string.location_is_off)

@Composable
fun getLocationDescriptionText(isLocationEnabled: Boolean) = if (isLocationEnabled)
    stringResource(id = R.string.tap_to_turn_it_on)
else stringResource(id = R.string.tap_to_turn_it_off)
