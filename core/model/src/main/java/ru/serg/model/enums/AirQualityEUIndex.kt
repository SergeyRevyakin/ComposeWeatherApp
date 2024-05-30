package ru.serg.model.enums

import ru.serg.strings.R.string

enum class AirQualityEUIndex(
    val id: Int,
    val indexDescription: Int,
    val generalPopulationEffect: Int,
    val sensitivePopulationEffect: Int,
) {
    GOOD(
        1,
        string.aqi_eu_index_1,
        string.aqi_eu_general_pop_desc_1,
        string.aqi_eu_sensitive_pop_desc_1
    ),
    FAIR(
        2,
        string.aqi_eu_index_2,
        string.aqi_eu_general_pop_desc_2,
        string.aqi_eu_sensitive_pop_desc_2
    ),
    MODERATE(
        3,
        string.aqi_eu_index_3,
        string.aqi_eu_general_pop_desc_3,
        string.aqi_eu_sensitive_pop_desc_3
    ),
    POOR(
        4,
        string.aqi_eu_index_4,
        string.aqi_eu_general_pop_desc_4,
        string.aqi_eu_sensitive_pop_desc_4
    ),
    VERY_POOR(
        5,
        string.aqi_eu_index_5,
        string.aqi_eu_general_pop_desc_5,
        string.aqi_eu_sensitive_pop_desc_5
    ),
    EXTREMELY_POOR(
        6,
        string.aqi_eu_index_6,
        string.aqi_eu_general_pop_desc_6,
        string.aqi_eu_sensitive_pop_desc_6
    ),
    UNKNOWN(
        -1,
        string.aqi_eu_index_unknown,
        string.aqi_eu_index_unknown,
        string.aqi_eu_index_unknown
    )
}