package ru.serg.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class NetworkStatusTest {

    @Test
    fun `test isNetworkConnected when network is available`() {
        val context = mock(Context::class.java)
        val connectivityManager = mock(ConnectivityManager::class.java)
        val network = mock(Network::class.java)

        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(mock())

        val networkStatus = NetworkStatus(context)
        assertEquals(true, networkStatus.isNetworkConnected())
    }

    @Test
    fun `test isNetworkConnected when network is unavailable`() {
        val context = mock(Context::class.java)
        val connectivityManager = mock(ConnectivityManager::class.java)

        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        `when`(connectivityManager.activeNetwork).thenReturn(null)

        val networkStatus = NetworkStatus(context)
        assertEquals(false, networkStatus.isNetworkConnected())
    }

}
