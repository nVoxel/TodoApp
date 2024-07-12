package com.voxeldev.todoapp.domain.usecase.base

import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Base use case that provides async task execution.
 * @author nvoxel
 */
abstract class BaseUseCase<in Params, out Type>(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val mainDispatcher: CoroutineDispatcher = coroutineDispatcherProvider.mainDispatcher,
    private val asyncDispatcher: CoroutineDispatcher = coroutineDispatcherProvider.ioDispatcher,
) where Type : Any {

    abstract suspend fun run(params: Params): Result<Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        onResult: suspend (Result<Type>) -> Unit = {},
    ) {
        scope.launch(mainDispatcher) {
            val deferred = async(asyncDispatcher) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    object NoParams
}
