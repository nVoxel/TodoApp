package com.voxeldev.todoapp.ui.viewmodel.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.token.GetAuthTokenUseCase
import com.voxeldev.todoapp.ui.navigation.state.AuthTokenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Used to store current authentication state in app.
 * @author nvoxel
 */
internal class NavigationViewModel(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
) : ViewModel() {

    private val _authTokenState: MutableStateFlow<AuthTokenState> = MutableStateFlow(value = AuthTokenState.Loading)
    val authTokenState: StateFlow<AuthTokenState> = _authTokenState.asStateFlow()

    init {
        checkAuthToken()
    }

    private fun checkAuthToken() {
        getAuthTokenUseCase(
            params = BaseUseCase.NoParams,
            scope = viewModelScope,
        ) { result ->
            result.fold(
                onSuccess = { _authTokenState.update { AuthTokenState.Found } },
                onFailure = { _authTokenState.update { AuthTokenState.NotFound } },
            )
        }
    }
}
