package com.lm.notes.data.remote_data.internet

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn


interface InternetStatusProvider {

    suspend fun startInternetListener(connectivityManager: ConnectivityManager): Flow<Boolean>

    suspend fun callback(status: (Boolean) -> Unit): ConnectivityManager.NetworkCallback

    class Base : InternetStatusProvider {
        override suspend fun startInternetListener(connectivityManager: ConnectivityManager) =
            callbackFlow {
                connectivityManager.apply {
                    getNetworkCapabilities(activeNetwork).also { cap ->
                        trySendBlocking(
                            if (cap != null) {
                                when {
                                    cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false
                                    cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false
                                    cap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false
                                    else -> false
                                }
                            } else true
                        )
                        callback {
                            trySendBlocking(it)
                        }.apply {
                            registerDefaultNetworkCallback(this)
                            awaitClose { unregisterNetworkCallback(this) }
                        }
                    }
                }
            }.flowOn(IO)

        override suspend fun callback(status: (Boolean) -> Unit) =
            object: ConnectivityManager.NetworkCallback(){

                override fun onAvailable(network: Network) { status(false) }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    status(true)
                }
            }
    }
}