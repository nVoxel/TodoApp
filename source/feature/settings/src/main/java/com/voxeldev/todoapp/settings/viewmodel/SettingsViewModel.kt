package com.voxeldev.todoapp.settings.viewmodel

import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.preferences.GetAppThemeUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.ClearTodoItemsUseCase
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.settings.ui.SettingsScreen
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Stores [SettingsScreen] current state, provides screen-related methods.
 * @author nvoxel
 */
class SettingsViewModel(
    private val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    private val clearTodoItemsUseCase: ClearTodoItemsUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase,
    networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    networkObserver = networkObserver,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val _appTheme: MutableStateFlow<AppTheme> = MutableStateFlow(value = AppTheme.Auto)
    val appTheme: StateFlow<AppTheme> = _appTheme.asStateFlow()

    init {
        getAppThemeUseCase(
            params = BaseUseCase.NoParams,
            scope = scope,
        ) { result ->
            result.onSuccess { appTheme ->
                _appTheme.update { appTheme }
            }
        }
    }

    fun changeSelectedAppTheme(appTheme: AppTheme) {
        _appTheme.update { appTheme }
    }

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
                onSuccess = { clearLocalData(successCallback = successCallback) },
                onFailure = ::handleException,
            )
        }
    }

    private fun clearLocalData(successCallback: () -> Unit) {
        clearTodoItemsUseCase(
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
