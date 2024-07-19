package com.voxeldev.todoapp.repository.utils

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.database.todo.TodoEntity
import com.voxeldev.todoapp.database.todo.TodoEntityList
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import com.voxeldev.todoapp.repository.mapper.TodoItemLocalMapper
import com.voxeldev.todoapp.repository.mapper.TodoItemRemoteMapper
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal class DefaultDataMerger @Inject constructor(
    private val todoItemLocalMapper: TodoItemLocalMapper,
    private val todoItemRemoteMapper: TodoItemRemoteMapper,
) : DataMerger {

    override suspend fun areContentsTheSame(localList: TodoEntityList, remoteList: TodoListResponse): Boolean =
       localList.isNotEmpty() && getLocalListRevision(localList = localList) == remoteList.revision

    override suspend fun mergeLists(
        localList: TodoEntityList,
        remoteList: TodoListResponse,
        deviceId: String,
        onRequestCreateLocalItem: suspend (item: TodoItem) -> Unit,
        onRequestUpdateLocalItem: suspend (item: TodoItem) -> Unit,
        onRequestDeleteLocalItem: suspend (id: String) -> Unit,
        onRequestCreateRemoteItem: suspend (item: TodoItem) -> Unit,
        onRequestUpdateRemoteItem: suspend (item: TodoItem) -> Unit,
        onRequestDeleteRemoteItem: suspend (id: String) -> Unit,
    ) {
        checkLocalList(
            localList = localList,
            remoteList = remoteList,
            onRequestUpdateLocalItem = onRequestUpdateLocalItem,
            onRequestDeleteLocalItem = onRequestDeleteLocalItem,
            onRequestCreateRemoteItem = onRequestCreateRemoteItem,
            onRequestUpdateRemoteItem = onRequestUpdateRemoteItem,
            onRequestDeleteRemoteItem = onRequestDeleteRemoteItem,
        )

        checkRemoteList(
            localList = localList,
            remoteList = remoteList,
            onRequestCreateLocalItem = onRequestCreateLocalItem,
        )
    }

    private suspend fun checkLocalList(
        localList: TodoEntityList,
        remoteList: TodoListResponse,
        onRequestUpdateLocalItem: suspend (item: TodoItem) -> Unit,
        onRequestDeleteLocalItem: suspend (id: String) -> Unit,
        onRequestCreateRemoteItem: suspend (item: TodoItem) -> Unit,
        onRequestUpdateRemoteItem: suspend (item: TodoItem) -> Unit,
        onRequestDeleteRemoteItem: suspend (id: String) -> Unit,
    ) {
        localList.forEach { localItem ->
            if (localItem.shouldBeCreated && !localItem.shouldBeDeleted) {
                onRequestCreateRemoteItem(todoItemLocalMapper.toModel(todoEntity = localItem))
                return@forEach
            }

            if (localItem.shouldBeDeleted) {
                onRequestDeleteRemoteItem(localItem.id)
                return@forEach
            }

            if (localItem.shouldBeUpdated) {
                onRequestUpdateRemoteItem(todoItemLocalMapper.toModel(todoEntity = localItem))
                return@forEach
            }

            val remoteItem = remoteList.list.find { item -> item.id == localItem.id }

            remoteItem?.let {
                if (checkItemsTheSame(localItem = localItem, remoteItem = remoteItem)) return@forEach

                if (localItem.modifiedTimestamp > remoteItem.modifiedTimestamp) {
                    onRequestUpdateRemoteItem(todoItemLocalMapper.toModel(todoEntity = localItem))
                } else {
                    onRequestUpdateLocalItem(todoItemRemoteMapper.toModel(todoItemData = remoteItem))
                }
            } ?: run {
                if (localItem.revision > remoteList.revision) {
                    onRequestCreateRemoteItem(todoItemLocalMapper.toModel(todoEntity = localItem))
                } else {
                    onRequestDeleteLocalItem(localItem.id)
                }
            }
        }
    }

    private suspend fun checkRemoteList(
        localList: TodoEntityList,
        remoteList: TodoListResponse,
        onRequestCreateLocalItem: suspend (item: TodoItem) -> Unit,
    ) {
        remoteList.list.forEach { remoteItem ->
            if (localList.find { localItem -> localItem.id == remoteItem.id } == null) {
                onRequestCreateLocalItem(todoItemRemoteMapper.toModel(todoItemData = remoteItem))
            }
        }
    }

    private fun checkItemsTheSame(localItem: TodoEntity, remoteItem: TodoItemData): Boolean =
        localItem.modifiedTimestamp == remoteItem.modifiedTimestamp

    private fun getLocalListRevision(localList: TodoEntityList) = localList.maxOf { it.revision }
}
