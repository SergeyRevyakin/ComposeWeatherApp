package ru.serg.main_pager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import ru.serg.model.WeatherItem

fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    })
}

fun WeatherItem.isExpired(
    frequency: Double
): Boolean {
    return (frequency * 60L * 60L * 1000L + cityItem.lastTimeUpdated) - System.currentTimeMillis() < 0
}