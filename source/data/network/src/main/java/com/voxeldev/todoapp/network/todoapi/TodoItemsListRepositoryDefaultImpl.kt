package com.voxeldev.todoapp.network.todoapi

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.network.base.BaseNetworkRepository
import com.voxeldev.todoapp.network.todoapi.datasource.URIs.LIST_URI
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import com.voxeldev.todoapp.network.todoapi.mapper.TodoListMapper
import com.voxeldev.todoapp.utils.exceptions.RevisionNotAvailableException
import com.voxeldev.todoapp.utils.platform.NetworkHandler
import io.ktor.client.HttpClient
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal class TodoItemsListRepositoryDefaultImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val httpClient: HttpClient,
    private val todoListMapper: TodoListMapper,
) : TodoItemListRepository, BaseNetworkRepository<List<TodoItem>>(networkHandler = networkHandler) {

    private val todoItemsFlow: MutableStateFlow<TodoItemList> = MutableStateFlow(value = listOf())

    private val lastRevisionFlow: MutableStateFlow<Int?> = MutableStateFlow(value = null)

    override suspend fun getAllFlow(): Result<Flow<TodoItemList>> =
        refreshData().fold(
            onSuccess = { return Result.success(todoItemsFlow) },
            onFailure = { exception -> return Result.failure(exception = exception) },
        )

    override suspend fun refreshData(): Result<Unit> =
        getAllList().fold(
            onSuccess = { list ->
                todoItemsFlow.update { list }
                return Result.success(Unit)
            },
            onFailure = { exception -> return Result.failure(exception = exception) },
        )

    override suspend fun getRevision(): Result<Int> =
        lastRevisionFlow.value?.let { revision -> Result.success(revision) }
            ?: Result.failure(RevisionNotAvailableException())

    private suspend fun getAllList() =
        doRequest<TodoListResponse>(
            request = httpClient.prepareGet {
                url(urlString = LIST_URI)
            },
            transform = {
                lastRevisionFlow.update { revision }
                todoListMapper.toModel(response = this)
            },
        )
}
