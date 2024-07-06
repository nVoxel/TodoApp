package com.voxeldev.todoapp.utils.platform

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Observes network availability changes.
 * @author nvoxel
 */
@SuppressLint("MissingPermission")
@Singleton
class NetworkObserver @Inject constructor(
    context: Context,
    networkHandler: NetworkHandler,
) {

    private val _networkAvailability: MutableStateFlow<Boolean> = MutableStateFlow(networkHandler.isNetworkAvailable())
    val networkAvailability: StateFlow<Boolean> = _networkAvailability.asStateFlow()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        .build()

    init {
        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                _networkAvailability.update { true }
            }

            override fun onLost(network: Network) {
                _networkAvailability.update { false }
            }
        },
        )
    }
}
