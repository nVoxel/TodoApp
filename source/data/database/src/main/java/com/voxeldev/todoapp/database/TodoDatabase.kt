package com.voxeldev.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.voxeldev.todoapp.database.todo.TodoDao
import com.voxeldev.todoapp.database.todo.TodoEntity
import com.voxeldev.todoapp.database.todo.converter.TodoItemImportanceConverters

/**
 * @author nvoxel
 */
@Database(entities = [TodoEntity::class], version = 1)
@TypeConverters(TodoItemImportanceConverters::class)
internal abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

internal const val DB_FILE_NAME = "todo.db"
