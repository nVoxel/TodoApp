package com.voxeldev.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @author nvoxel
 */
class MainActivityViewModel : ViewModel() {

    private val _authResultFlow: MutableStateFlow<YandexAuthResult?> = MutableStateFlow(value = null)
    val authResultFlow: StateFlow<YandexAuthResult?> = _authResultFlow.asStateFlow()

    fun onAuthResult(authResult: YandexAuthResult) {
        _authResultFlow.update { authResult }
    }

    fun onAuthSuccess() {
        _authResultFlow.update { null }
    }
}
