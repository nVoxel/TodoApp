package com.voxeldev.todoapp.domain.usecase.preferences

import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
class SetAutoRefreshIntervalUseCase(
    private val preferencesRepository: PreferencesRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<Long, Unit>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: Long): Result<Unit> =
        preferencesRepository.setAutoRefreshInterval(autoRefreshInterval = params)
}
