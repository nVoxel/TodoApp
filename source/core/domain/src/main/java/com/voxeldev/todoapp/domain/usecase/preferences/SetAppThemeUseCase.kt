package com.voxeldev.todoapp.domain.usecase.preferences

import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class SetAppThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<AppTheme, Unit>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: AppTheme): Result<Unit> =
        preferencesRepository.setAppTheme(appTheme = params)
}
