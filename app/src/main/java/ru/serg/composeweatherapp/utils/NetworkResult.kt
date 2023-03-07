package ru.serg.composeweatherapp.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class NetworkResult<T>(
    val data: T? = null,
    var message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String?, data: T? = null) :
        NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()
}
