package com.voxeldev.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voxeldev.todoapp.domain.usecase.preferences.GetAppThemeUseCase
import com.voxeldev.todoapp.domain.usecase.preferences.SetAppThemeUseCase
import javax.inject.Inject

/**
 * @author nvoxel
 */
class MainActivityViewModelProvider @Inject constructor(
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainActivityViewModel(
            getAppThemeUseCase = getAppThemeUseCase,
            setAppThemeUseCase = setAppThemeUseCase,
        ) as T
}
