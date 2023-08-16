package ru.serg.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class NetworkResult<out T> {

    class Success<T>(val data: T) : NetworkResult<T>()

    class Error<Nothing>(val message: String?) :
        NetworkResult<Nothing>()

    object Loading : NetworkResult<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<NetworkResult<T>> {
    return this
        .map<T, NetworkResult<T>> {
            NetworkResult.Success(it)
        }
        .onStart { emit(NetworkResult.Loading) }
        .catch { emit(NetworkResult.Error(it.localizedMessage)) }
}