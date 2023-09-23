package com.serg.weather

import ru.serg.drawables.R.drawable

object IconMapper {
    fun map(iconId: Int?): Int {
        return when (iconId) {
            200, 201, 202, 230, 231, 232 -> drawable.ic_thunderstorm
            210, 211, 212, 221 -> drawable.ic_lightning
            300, 301, 321, 500 -> drawable.ic_sprinkle
            302, 311, 312, 314, 501, 502, 503, 504 -> drawable.ic_rain
            313, 520, 521, 522 -> drawable.ic_showers
            310, 511, 611, 612, 615, 616, 620 -> drawable.ic_rain_mix
            531, 901 -> drawable.ic_storm_showers
            600, 601, 621, 622 -> drawable.ic_snow
            602 -> drawable.ic_sleet
            711 -> drawable.ic_smoke
            721 -> drawable.ic_day_haze
            701, 731, 761, 762 -> drawable.ic_dust
            771, 801, 802, 803 -> drawable.ic_cloudy_gusts
            781, 900 -> drawable.ic_tornado
            800 -> drawable.ic_day_sunny
            804 -> drawable.ic_cloudy
            902 -> drawable.ic_hurricane
            903 -> drawable.ic_snowflake_cold
            904 -> drawable.ic_hot
            905 -> drawable.ic_windy
            906 -> drawable.ic_hail
            957 -> drawable.ic_strong_wind
            else -> drawable.ic_sun
        }
    }
}