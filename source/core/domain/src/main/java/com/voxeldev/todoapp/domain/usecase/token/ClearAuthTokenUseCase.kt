package com.voxeldev.todoapp.domain.usecase.token

import com.voxeldev.todoapp.api.model.AuthToken
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * Clears saved [AuthToken].
 * @author nvoxel
 */
class ClearAuthTokenUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<BaseUseCase.NoParams, Unit>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: NoParams): Result<Unit> =
        authTokenRepository.clearToken()
}
