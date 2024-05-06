package ru.serg.model

import kotlinx.serialization.Serializable

@Serializable
data class AirPollution(
    val co: Double, // Carbon monoxide
    val nh3: Double, //Ammonia
    val no: Double, //Nitrogen monoxide
    val no2: Double, //Nitrogen dioxide
    val o3: Double, //Ozone
    val pm10: Double, //PM10
    val pm25: Double, //PM2.5
    val so2: Double, //Sulphur dioxide
) {
    fun getMaxPollutionValue(): Double {
        val list = listOf(
            this.co,
            this.nh3,
            this.no,
            this.no2,
            this.o3,
            this.pm10,
            this.pm25,
            this.so2,
        )

        return list.maxOrNull() ?: 0.0
    }
}
