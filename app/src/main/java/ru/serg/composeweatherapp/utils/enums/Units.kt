package ru.serg.composeweatherapp.utils.enums

enum class Units(
    val parameterCode: String,
    val title: String,
    val description: String,
    val tempUnits: String,
    val windUnits: String
) {
    METRIC(
        "metric",
        "Metric",
        "Temperature in Celsius, wind speed in meter/sec",
        "℃",
        "m/s"
    ),
    IMPERIAL(
        "imperial",
        "Imperial",
        "Temperature in Fahrenheit and wind speed in miles/hour",
        "℉",
        "mph"
    ),
    KELVIN(
        "standard",
        "Kelvin",
        "Temperature in Kelvin and wind speed in meter/sec",
        "K",
        "m/s"
    )
}