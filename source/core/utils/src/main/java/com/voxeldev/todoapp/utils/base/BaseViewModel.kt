package com.voxeldev.todoapp.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel that can store exceptions, loading and network states.
 * @author nvoxel
 */
open class BaseViewModel(
    networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    protected val _exception: MutableStateFlow<Exception?> = MutableStateFlow(value = null)
    val exception: StateFlow<Exception?> = _exception.asStateFlow()

    protected val _loading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    protected val _networkNotification: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val networkNotification: StateFlow<Boolean> = _networkNotification.asStateFlow()

    protected val scope = CoroutineScope(SupervisorJob() + coroutineDispatcherProvider.defaultDispatcher)

    init {
        viewModelScope.launch {
            networkObserver.networkAvailability.collect { networkAvailable ->
                _networkNotification.update { !networkAvailable }

                if (networkAvailable && exception.value is NetworkNotAvailableException) {
                    onNetworkConnected()
                }
            }
        }
    }

    /**
     * Called when a network connection appears, if the previous request was not completed due to network unavailability
     */
    protected open fun onNetworkConnected() {}

    protected fun handleException(exception: Throwable) {
        _exception.update {
            if (exception is Exception) exception else Exception(exception)
        }
        _loading.update { false }
    }

    fun hideNetworkNotification() {
        _networkNotification.update { false }
    }

    override fun onCleared() {
        scope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}
