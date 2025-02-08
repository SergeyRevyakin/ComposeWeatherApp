package ru.serg.weather

import java.math.RoundingMode


fun Double?.orZero() = this ?: 0.0

fun Double?.roundedOrZero() = this?.toBigDecimal()?.setScale(1, RoundingMode.UP)?.toDouble() ?: 0.0

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0

fun String?.orEmpty() = this ?: ""

fun Int?.toTimeStamp() = this?.let { it * 1000L } ?: 0L

fun Long?.toTimeStamp() = this?.let { it * 1000L } ?: 0L