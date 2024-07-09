package com.voxeldev.todoapp.auth.viewmodel

import com.voxeldev.todoapp.api.model.AuthToken
import com.voxeldev.todoapp.api.model.AuthTokenType
import com.voxeldev.todoapp.auth.exceptions.FailedToAuthenticateException
import com.voxeldev.todoapp.auth.exceptions.FieldNotFilledException
import com.voxeldev.todoapp.auth.ui.AuthScreen
import com.voxeldev.todoapp.auth.ui.AuthScreenState
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.domain.usecase.token.SetAuthTokenUseCase
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import com.voxeldev.todoapp.utils.exceptions.TokenNotFoundException
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Stores [AuthScreen] current state and manages authentication state.
 * @author nvoxel
 */
class AuthViewModel(
    private val yandexAuthResultFlow: StateFlow<YandexAuthResult?>,
    private val setAuthTokenUseCase: SetAuthTokenUseCase,
    private val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    private val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase, // there is no other way to check auth
    private val networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    networkObserver = networkObserver,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val _state: MutableStateFlow<AuthScreenState> = MutableStateFlow(value = AuthScreenState.ChooseMethod())
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    init {
        scope.launch {
            yandexAuthResultFlow.collect { authResult ->
                when (authResult) {
                    is YandexAuthResult.Success -> {
                        _state.update { AuthScreenState.ChooseMethod(oauthToken = authResult.token.value) }
                        _loading.update { true }
                        checkAuth()
                    }

                    is YandexAuthResult.Failure -> {
                        _exception.update { FailedToAuthenticateException() }
                    }

                    else -> {}
                }
            }
        }

        scope.launch {
            networkObserver.networkAvailability.collect { networkAvailable ->
                if (networkAvailable) checkAuth()
            }
        }
    }

    fun onRetryClicked() {
        _exception.update { null }
    }

    fun onChooseMethodClicked() {
        _state.update { AuthScreenState.ChooseMethod() }
    }

    fun onBearerMethodClicked() {
        _state.update { AuthScreenState.BearerMethod() }
    }

    fun onUpdateLoginText(loginText: String) {
        val screenState = state.value
        if (screenState !is AuthScreenState.BearerMethod) return

        _state.update { screenState.copy(login = loginText) }
    }

    fun onUpdatePasswordText(passwordText: String) {
        val screenState = state.value
        if (screenState !is AuthScreenState.BearerMethod) return

        _state.update { screenState.copy(password = passwordText) }
    }

    fun checkAuth() {
        if (!networkObserver.networkAvailability.value) {
            handleException(exception = NetworkNotAvailableException())
            return
        }

        _loading.update { true }
        setToken(successCallback = ::checkAuthInternal)
    }

    private fun setToken(successCallback: () -> Unit) {
        val authToken = getAuthToken() ?: return

        if (authToken.token.isBlank()) {
            handleException(exception = FieldNotFilledException())
            return
        }

        setAuthTokenUseCase(
            params = authToken,
            scope = scope,
        ) { result ->
            result.fold(
                onSuccess = { successCallback() },
                onFailure = { exception ->
                    handleException(exception = exception)
                },
            )
        }
    }

    private fun getAuthToken(): AuthToken? {
        return when (val screenState = state.value) {
            is AuthScreenState.ChooseMethod -> AuthToken(
                token = screenState.oauthToken ?: return null,
                type = AuthTokenType.OAuth,
            )

            is AuthScreenState.BearerMethod -> AuthToken(
                token = screenState.password,
                type = AuthTokenType.Bearer,
            )

            else -> {
                _loading.update { false }
                return null
            }
        }
    }

    private fun checkAuthInternal() {
        getAllTodoItemsFlowUseCase(
            params = BaseUseCase.NoParams,
            scope = scope,
        ) { result ->
            result.fold(
                onSuccess = { todoItemList ->
                    onAuthSuccess()
                },
                onFailure = { exception -> onAuthFailure(exception = exception) },
            )
        }
    }

    private fun onAuthSuccess() {
        _state.update { AuthScreenState.Success }
        _loading.update { false }
    }

    private fun onAuthFailure(exception: Throwable) {
        _exception.update {
            if (exception is TokenNotFoundException) {
                FailedToAuthenticateException()
            } else {
                exception as Exception
            }
        }
        clearToken()
    }

    private fun clearToken() {
        clearAuthTokenUseCase(
            params = BaseUseCase.NoParams,
            scope = scope,
        ) { result ->
            result.onFailure(action = ::handleException)
            _loading.update { false }
        }
    }
}
