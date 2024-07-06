package com.voxeldev.todoapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.domain.usecase.token.SetAuthTokenUseCase
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.yandex.authsdk.YandexAuthResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow

/**
 * @author nvoxel
 */
class AuthViewModelProvider @AssistedInject constructor(
    @Assisted val yandexAuthResultFlow: StateFlow<YandexAuthResult?>,
    private val setAuthTokenUseCase: SetAuthTokenUseCase,
    private val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    private val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase,
    private val networkObserver: NetworkObserver,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModelProvider.Factory {

    @AssistedFactory
    interface Factory {
        fun create(yandexAuthResultFlow: StateFlow<YandexAuthResult?>): AuthViewModelProvider
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T = AuthViewModel(
        yandexAuthResultFlow = yandexAuthResultFlow,
        setAuthTokenUseCase = setAuthTokenUseCase,
        clearAuthTokenUseCase = clearAuthTokenUseCase,
        getAllTodoItemsFlowUseCase = getAllTodoItemsFlowUseCase,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    ) as T
}
