package ru.serg.model

import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@Serializable
data class AirQuality(
    val co: Double, // Carbon monoxide
    val nh3: Double, //Ammonia
    val no: Double, //Nitrogen monoxide
    val no2: Double, //Nitrogen dioxide
    val o3: Double, //Ozone
    val pm10: Double, //PM10
    val pm25: Double, //PM2.5
    val so2: Double, //Sulphur dioxide
    val owmIndex: Int,
) {
    companion object {
        fun blankAirQuality() = AirQuality(
            owmIndex = 2,
            co = 0.0,
            nh3 = 0.0,
            no = 0.0,
            no2 = 0.0,
            o3 = 0.0,
            pm10 = 0.0,
            pm25 = 0.0,
            so2 = 0.0
        )
    }

    private fun getMaxPollutionValue(): Double {
        val list = listOf(
            this.no2,
            this.o3,
            this.pm10,
            this.pm25,
        )

        return list.maxOrNull() ?: 0.0
    }

    fun getPollutionIndex(): Int {
        return when (getMaxPollutionValue().roundToInt()) {
            in 0..20 -> 1
            in 21..50 -> 2
            in 51..100 -> 3
            in 101..150 -> 4
            in 151..250 -> 5
            in 251..Int.MAX_VALUE -> 6
            else -> -1
        }
    }
}
