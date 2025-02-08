package ru.serg.weather

import ru.serg.model.AirQuality
import ru.serg.network.dto.AirQualityResponse

object DataMapper {

    fun mapAirQuality(responseItem: AirQualityResponse.AirQualityResponseItem?): AirQuality {
        return if (responseItem == null) AirQuality.blankAirQuality()
        else {
            AirQuality(
                co = responseItem.components?.co.orZero(),
                nh3 = responseItem.components?.nh3.orZero(),
                no = responseItem.components?.no.orZero(),
                no2 = responseItem.components?.no2.orZero(),
                o3 = responseItem.components?.o3.orZero(),
                pm10 = responseItem.components?.pm10.orZero(),
                pm25 = responseItem.components?.pm25.orZero(),
                so2 = responseItem.components?.so2.orZero(),
                owmIndex = responseItem.airQualityIndex?.aqi ?: 0,
            )
        }
    }
}