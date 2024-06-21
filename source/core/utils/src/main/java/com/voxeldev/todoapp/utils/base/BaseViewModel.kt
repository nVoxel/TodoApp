package com.voxeldev.todoapp.utils.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author nvoxel
 */
abstract class BaseViewModel : ViewModel() {

    protected val _exception: MutableStateFlow<Throwable?> = MutableStateFlow(value = null)
    val exception: StateFlow<Throwable?> = _exception

    protected val _loading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val loading: StateFlow<Boolean> = _loading

    protected fun handleException(exception: Throwable) {
        _exception.value = exception
        _loading.value = false
    }
}
