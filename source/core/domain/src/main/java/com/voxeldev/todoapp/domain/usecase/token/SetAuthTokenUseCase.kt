package com.voxeldev.todoapp.domain.usecase.token

import com.voxeldev.todoapp.api.model.AuthToken
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class SetAuthTokenUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<AuthToken, Unit>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: AuthToken): Result<Unit> =
        authTokenRepository.setToken(token = params)
}
