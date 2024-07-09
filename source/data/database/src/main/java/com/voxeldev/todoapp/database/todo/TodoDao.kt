package com.voxeldev.todoapp.database.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voxeldev.todoapp.api.model.TodoItemImportance
import kotlinx.coroutines.flow.Flow

/**
 * @author nvoxel
 */
@Dao
internal interface TodoDao {

    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos ORDER BY modified_timestamp DESC")
    suspend fun getAllTodosAsList(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity): Long

    // update item without touching flags
    @Query(
        "UPDATE todos SET " +
                "text = :text, " +
                "importance = :importance, " +
                "deadline = :deadline, " +
                "is_complete = :isComplete, " +
                "creation_timestamp = :creationTimestamp, " +
                "modified_timestamp = :modifiedTimestamp, " +
                "last_updated_by = :lastUpdatedBy, " +
                "revision = :revision " +
                "WHERE id = :id",
    )
    suspend fun updateTodo(
        id: String,
        text: String,
        importance: TodoItemImportance,
        deadline: Long?,
        isComplete: Boolean,
        creationTimestamp: Long,
        modifiedTimestamp: Long,
        lastUpdatedBy: String,
        revision: Int,
    )

    @Query("UPDATE todos SET should_be_created = :shouldBeCreated WHERE id = :id")
    suspend fun setItemShouldBeCreated(id: String, shouldBeCreated: Boolean)

    @Query("UPDATE todos SET should_be_updated = :shouldBeUpdated WHERE id = :id")
    suspend fun setItemShouldBeUpdated(id: String, shouldBeUpdated: Boolean)

    @Query("UPDATE todos SET should_be_deleted = :shouldBeDeleted WHERE id = :id")
    suspend fun setItemShouldBeDeleted(id: String, shouldBeDeleted: Boolean)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteSingleTodo(id: String)

    @Query("DELETE FROM todos")
    suspend fun clearTable()
}
