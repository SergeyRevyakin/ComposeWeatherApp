package ru.serg.model

data class AlertItem(
    val startAt: Long,
    val endAt: Long,
    val title: String,
    val description: String
)