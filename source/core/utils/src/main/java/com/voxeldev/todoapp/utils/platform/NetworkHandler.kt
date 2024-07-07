package com.voxeldev.todoapp.utils.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import javax.inject.Inject

/**
 * Returns whether a network connection is available or not.
 * @author nvoxel
 */
@AppScope
class NetworkHandler @Inject constructor(
    private val context: Context,
) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}
