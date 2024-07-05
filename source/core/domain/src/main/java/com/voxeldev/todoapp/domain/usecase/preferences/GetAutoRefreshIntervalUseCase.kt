package com.voxeldev.todoapp.domain.usecase.preferences

import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class GetAutoRefreshIntervalUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<BaseUseCase.NoParams, Long>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: NoParams): Result<Long> = preferencesRepository.getAutoRefreshInterval()
}
