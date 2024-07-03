package com.voxeldev.todoapp.auth.viewmodel

import com.voxeldev.todoapp.api.model.AuthToken
import com.voxeldev.todoapp.api.model.AuthTokenType
import com.voxeldev.todoapp.auth.exceptions.FailedToAuthenticateException
import com.voxeldev.todoapp.auth.exceptions.FieldNotFilledException
import com.voxeldev.todoapp.auth.ui.AuthScreenState
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.domain.usecase.token.SetAuthTokenUseCase
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.exceptions.TokenNotFoundException
import com.voxeldev.todoapp.utils.platform.LinkHandler
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author nvoxel
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val setAuthTokenUseCase: SetAuthTokenUseCase,
    private val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    private val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase, // there is no other way to check auth
    private val linkHandler: LinkHandler,
    stringResourceProvider: StringResourceProvider,
    networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    networkObserver = networkObserver,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val oauthUrl = stringResourceProvider.getTodoOAuthUrl()

    private val _state: MutableStateFlow<AuthScreenState> = MutableStateFlow(value = AuthScreenState.ChooseMethod)
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    fun onRetryClicked() {
        if (exception.value is FailedToAuthenticateException) {
            when (state.value) {
                is AuthScreenState.BearerMethod -> onBearerMethodClicked()
                is AuthScreenState.OAuthMethod -> onOAuthMethodContinueClicked()
                else -> {}
            }
        }

        _exception.update { null }
    }

    fun onChooseMethodClicked() {
        _state.update { AuthScreenState.ChooseMethod }
    }

    fun onBearerMethodClicked() {
        _state.update { AuthScreenState.BearerMethod() }
    }

    fun onOAuthMethodClicked() {
        _state.update { AuthScreenState.OAuthMethodInfo }
    }

    fun onOAuthMethodContinueClicked() {
        linkHandler.openLink(oauthUrl)
        _state.update { AuthScreenState.OAuthMethod() }
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

    fun onUpdateTokenText(tokenText: String) {
        val screenState = state.value
        if (screenState !is AuthScreenState.OAuthMethod) return

        _state.update { screenState.copy(token = tokenText) }
    }

    fun checkAuth() {
        _loading.update { true }

        setToken {
            getAllTodoItemsFlowUseCase(
                params = BaseUseCase.NoParams,
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = {
                        _state.update { AuthScreenState.Success }
                        _loading.update { false }
                    },
                    onFailure = { exception ->
                        _exception.update {
                            if (exception is TokenNotFoundException) {
                                FailedToAuthenticateException()
                            } else {
                                exception
                            }
                        }
                        clearToken()
                    },
                )
            }
        }
    }

    private fun setToken(successCallback: () -> Unit) {
        val authToken = when (val screenState = state.value) {
            is AuthScreenState.BearerMethod -> AuthToken(
                token = screenState.password,
                type = AuthTokenType.Bearer,
            )

            is AuthScreenState.OAuthMethod -> AuthToken(
                token = screenState.token,
                type = AuthTokenType.OAuth,
            )

            else -> {
                _loading.update { false }
                return
            }
        }

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

    private fun clearToken() {
        clearAuthTokenUseCase(
            params = BaseUseCase.NoParams,
            scope = scope,
        ) { result ->
            result.onFailure(action = ::handleException)
            _loading.update { false }
        }
    }

    override fun onNetworkConnected() = checkAuth()
}
