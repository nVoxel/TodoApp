package com.voxeldev.todoapp.ui.viewmodel.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voxeldev.todoapp.domain.usecase.token.GetAuthTokenUseCase
import javax.inject.Inject

/**
 * @author nvoxel
 */
class NavigationViewModelProvider @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NavigationViewModel(getAuthTokenUseCase = getAuthTokenUseCase) as T
}
