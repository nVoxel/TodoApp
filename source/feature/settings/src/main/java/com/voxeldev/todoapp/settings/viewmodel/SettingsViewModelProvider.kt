package com.voxeldev.todoapp.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class SettingsViewModelProvider @Inject constructor(
    private val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    private val networkObserver: NetworkObserver,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(
        clearAuthTokenUseCase = clearAuthTokenUseCase,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    ) as T
}
