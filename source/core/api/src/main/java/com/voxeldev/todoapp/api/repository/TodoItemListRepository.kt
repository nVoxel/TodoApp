package com.voxeldev.todoapp.api.repository

import com.voxeldev.todoapp.api.model.TodoItemList
import kotlinx.coroutines.flow.Flow

/**
 * @author nvoxel
 */
interface TodoItemListRepository {

    suspend fun getAllFlow(): Result<Flow<TodoItemList>>

    suspend fun refreshData(): Result<Unit>

    suspend fun getRevision(): Result<Int>
}
