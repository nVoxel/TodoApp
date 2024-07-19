package com.voxeldev.todoapp.utils.platform

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Observes network availability changes.
 * @author nvoxel
 */
@AppScope
@SuppressLint("MissingPermission")
class NetworkObserver @Inject constructor(
    context: Context,
    private val networkHandler: NetworkHandler,
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
                    _networkAvailability.update { networkHandler.isNetworkAvailable() } // fix false positives
                }

                override fun onLost(network: Network) {
                    _networkAvailability.update { networkHandler.isNetworkAvailable() } // fix false positives
                }
            },
        )
    }
}
