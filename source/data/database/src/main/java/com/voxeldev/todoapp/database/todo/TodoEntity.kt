package com.voxeldev.todoapp.database.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.voxeldev.todoapp.api.model.TodoItemImportance

/**
 * @author nvoxel
 */
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "importance") val importance: TodoItemImportance,
    @ColumnInfo(name = "deadline") val deadlineTimestamp: Long? = null,
    @ColumnInfo(name = "is_complete") val isComplete: Boolean,
    @ColumnInfo(name = "creation_timestamp") val creationTimestamp: Long,
    @ColumnInfo(name = "modified_timestamp") val modifiedTimestamp: Long,
    @ColumnInfo(name = "last_updated_by") val lastUpdatedBy: String,
    @ColumnInfo(name = "revision") val revision: Int,
    @ColumnInfo(name = "should_be_created") val shouldBeCreated: Boolean = false,
    @ColumnInfo(name = "should_be_updated") val shouldBeUpdated: Boolean = false,
    @ColumnInfo(name = "should_be_deleted") val shouldBeDeleted: Boolean = false,
)
