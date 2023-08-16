package ru.serg.common


enum class Units(
    val parameterCode: String,
) {
    METRIC(
        Constants.METRIC,
    ),
    IMPERIAL(
        Constants.IMPERIAL,
    ),
    KELVIN(
        Constants.STANDARD,
    )
}