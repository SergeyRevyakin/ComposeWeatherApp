package ru.serg.composeweatherapp.api

interface OpenWeatherMapService {
    suspend fun getForecast()
}