package com.serg.network_self_proxy.dto

import kotlinx.serialization.Serializable


@Serializable
data class CityModel(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val county: String,
    val region: String?,
    val countyCode: String,
    val cityAdditionalInfo: CityAdditionalInfo?,
)

@Serializable
data class CityAdditionalInfo(
    val accuWeatherCityKey: String?,
    val countryNameFullEn: String?,
    val administrativeAreaEn: String?,
    val gtmOffset: Double?,
    val rating: Int?
)