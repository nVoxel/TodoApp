package com.voxeldev.todoapp.settings.viewmodel

import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.settings.ui.SettingsScreen
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Stores [SettingsScreen] current state, provides screen-related methods.
 * @author nvoxel
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    networkObserver = networkObserver,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    fun onRetryClicked() {
        _exception.update { null }
    }

    fun logOut(successCallback: () -> Unit) {
        _loading.update { true }

        clearAuthTokenUseCase(
            params = BaseUseCase.NoParams,
            scope = scope,
        ) { result ->
            result.fold(
                onSuccess = { successCallback() },
                onFailure = ::handleException,
            )
            _loading.update { false }
        }
    }
}
