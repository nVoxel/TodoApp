package com.voxeldev.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.preferences.GetAppThemeUseCase
import com.voxeldev.todoapp.domain.usecase.preferences.SetAppThemeUseCase
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Used to store current OAuth state.
 * @author nvoxel
 */
internal class MainActivityViewModel(
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
) : ViewModel() {

    private val _authResultFlow: MutableStateFlow<YandexAuthResult?> = MutableStateFlow(value = null)
    val authResultFlow: StateFlow<YandexAuthResult?> = _authResultFlow.asStateFlow()

    private val _appThemeFlow: MutableStateFlow<AppTheme> = MutableStateFlow(value = AppTheme.Auto)
    val appThemeFlow: StateFlow<AppTheme> = _appThemeFlow.asStateFlow()

    init {
        getAppThemeUseCase(
            params = BaseUseCase.NoParams,
            scope = viewModelScope,
        ) { result ->
            result.onSuccess { appTheme ->
                _appThemeFlow.update { appTheme }
            }
        }
    }

    fun onAuthResult(authResult: YandexAuthResult) {
        _authResultFlow.update { authResult }
    }

    fun onAuthSuccess() {
        _authResultFlow.update { null }
    }

    fun onChangeAppTheme(appTheme: AppTheme) {
        setAppThemeUseCase(
            params = appTheme,
            scope = viewModelScope,
        ) { result ->
            result.onSuccess {
                _appThemeFlow.update { appTheme }
            }
        }
    }
}
