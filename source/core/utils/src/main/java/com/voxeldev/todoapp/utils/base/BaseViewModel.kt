package com.voxeldev.todoapp.utils.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @author nvoxel
 */
abstract class BaseViewModel : ViewModel() {

    protected val _exception: MutableStateFlow<Throwable?> = MutableStateFlow(value = null)
    val exception: StateFlow<Throwable?> = _exception.asStateFlow()

    protected val _loading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    protected fun handleException(exception: Throwable) {
        _exception.update { exception }
        _loading.update { false }
    }
}
