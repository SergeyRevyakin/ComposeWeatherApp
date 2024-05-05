package ru.serg.weather

enum class WeatherCode(val id: Int) {
    GOOD_WEATHER(1),
    BAD_WEATHER(0)
}

fun isGoodWeather(weatherCode: WeatherCode) = weatherCode == WeatherCode.GOOD_WEATHER

fun mapWeatherCode(iconId: Int): WeatherCode {
    val badWeatherIdList = listOf(
        200,
        201,
        202,
        230,
        231,
        232,
        210,
        211,
        212,
        221,
        300,
        301,
        321,
        500,
        302,
        311,
        312,
        314,
        501,
        502,
        503,
        504,
        313,
        520,
        521,
        522,
        310,
        511,
        611,
        612,
        615,
        616,
        620,
        531,
        901,
        600,
        601,
        621,
        622,
        781,
        900
    )
    return if (badWeatherIdList.contains(iconId)) WeatherCode.BAD_WEATHER
    else WeatherCode.GOOD_WEATHER
}