package ru.serg.composeweatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class NetworkStatus(val context: Context) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeConnectivityFlow() = context.observeConnectivityAsFlow()

    fun isNetworkConnected() = context.currentConnectionsState == ConnectionState.Available
}

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

val Context.currentConnectionsState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): ConnectionState {
    val network = connectivityManager.activeNetwork
    network?.let {
        val actNetwork = connectivityManager.getNetworkCapabilities(network)
            ?: return ConnectionState.Unavailable
        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                ConnectionState.Available
            }
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                ConnectionState.Available
            }
            else -> {
                ConnectionState.Available
            }
        }
    }
    return ConnectionState.Unavailable
}

@ExperimentalCoroutinesApi
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = NetworkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    val currentState = getCurrentConnectivityState(connectivityManager)
    send(currentState)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }

        override fun onUnavailable() {
            callback(ConnectionState.Unavailable)
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            if (blocked) callback(ConnectionState.Unavailable)
        }
    }
}