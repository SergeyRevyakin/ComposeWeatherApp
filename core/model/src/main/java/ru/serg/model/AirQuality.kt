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

    fun getPM25PollutionIndex(): Int {
        return when (pm25.roundToInt()) {
            in 0..10 -> 1
            in 10..20 -> 2
            in 20..25 -> 3
            in 25..50 -> 4
            in 50..75 -> 5
            in 75..Int.MAX_VALUE -> 6
            else -> -1
        }
    }

    fun getPM10PollutionIndex(): Int {
        return when (pm10.roundToInt()) {
            in 0..20 -> 1
            in 20..40 -> 2
            in 40..50 -> 3
            in 50..100 -> 4
            in 100..150 -> 5
            in 150..Int.MAX_VALUE -> 6
            else -> -1
        }
    }

    fun getNO2PollutionIndex(): Int {
        return when (no2.roundToInt()) {
            in 0..40 -> 1
            in 40..90 -> 2
            in 90..120 -> 3
            in 120..230 -> 4
            in 230..340 -> 5
            in 340..Int.MAX_VALUE -> 6
            else -> -1
        }
    }

    fun getO3PollutionIndex(): Int {
        return when (o3.roundToInt()) {
            in 0..50 -> 1
            in 50..100 -> 2
            in 100..130 -> 3
            in 130..240 -> 4
            in 240..380 -> 5
            in 380..Int.MAX_VALUE -> 6
            else -> -1
        }
    }

    fun getSO2PollutionIndex(): Int {
        return when (so2.roundToInt()) {
            in 0..100 -> 1
            in 100..200 -> 2
            in 200..350 -> 3
            in 350..500 -> 4
            in 500..750 -> 5
            in 750..Int.MAX_VALUE -> 6
            else -> -1
        }
    }

    fun getEUPollutionIndex(): Int {
        return listOf(
            getPM25PollutionIndex(),
            getPM10PollutionIndex(),
            getNO2PollutionIndex(),
            getO3PollutionIndex(),
            getSO2PollutionIndex()
        ).max()
    }
}
