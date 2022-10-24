package ru.serg.composeweatherapp.utils

import ru.serg.composeweatherapp.R

object IconMapper {
    fun map(iconId: Int?): Int {
        return when (iconId) {
            200, 201, 202, 230, 231, 232 -> R.drawable.ic_thunderstorm
            210, 211, 212, 221 -> R.drawable.ic_lightning
            300, 301, 321, 500 -> R.drawable.ic_sprinkle
            302, 311, 312, 314, 501, 502, 503, 504 -> R.drawable.ic_rain
            313, 520, 521, 522, 701 -> R.drawable.ic_showers
            310, 511, 611, 612, 615, 616, 620 -> R.drawable.ic_rain_mix
            531, 901 -> R.drawable.ic_storm_showers
            600, 601, 621, 622 -> R.drawable.ic_snow
            602 -> R.drawable.ic_sleet
            711 -> R.drawable.ic_smoke
            721 -> R.drawable.ic_day_haze
            731, 761, 762 -> R.drawable.ic_dust
            771, 801, 802, 803 -> R.drawable.ic_cloudy_gusts
            781, 900 -> R.drawable.ic_tornado
            800 -> R.drawable.ic_day_sunny
            804 -> R.drawable.ic_cloudy
            902 -> R.drawable.ic_hurricane
            903 -> R.drawable.ic_snowflake_cold
            904 -> R.drawable.ic_hot
            905 -> R.drawable.ic_windy
            906 -> R.drawable.ic_hail
            957 -> R.drawable.ic_strong_wind
            else -> R.drawable.ic_sunny_day
        }
    }
}