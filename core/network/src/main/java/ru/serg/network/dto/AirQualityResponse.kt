package ru.serg.network.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityResponse(
    @SerialName("coord")
    val coordinates: Coord? = null,
    @SerialName("list")
    val list: List<AirQualityResponseItem>? = null
) {

    @Serializable
    data class AirQualityResponseItem(
        @SerialName("components")
        val components: Components? = null,
        @SerialName("dt")
        val timestamp: Int? = null,
        @SerialName("main")
        val airQualityIndex: AirQualityIndex? = null
    )

    @Serializable
    data class Components(
        @SerialName("co")
        val co: Double? = null,
        @SerialName("nh3")
        val nh3: Double? = null,
        @SerialName("no")
        val no: Double? = null,
        @SerialName("no2")
        val no2: Double? = null,
        @SerialName("o3")
        val o3: Double? = null,
        @SerialName("pm10")
        val pm10: Double? = null,
        @SerialName("pm2_5")
        val pm25: Double? = null,
        @SerialName("so2")
        val so2: Double? = null
    )

    @Serializable
    data class AirQualityIndex(
        @SerialName("aqi")
        val aqi: Int? = null
    )
}