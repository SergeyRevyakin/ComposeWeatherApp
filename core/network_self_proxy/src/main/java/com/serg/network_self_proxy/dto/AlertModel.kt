package com.serg.network_self_proxy.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlertModel(
    val title: String?,
    val description: String?,
    val startTime: Long?,
    val endTime: Long?,
)
