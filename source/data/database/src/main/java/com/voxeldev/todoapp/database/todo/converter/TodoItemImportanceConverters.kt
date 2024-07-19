package com.voxeldev.todoapp.database.todo.converter

import androidx.room.TypeConverter
import com.voxeldev.todoapp.api.model.TodoItemImportance

/**
 * @author nvoxel
 */
internal class TodoItemImportanceConverters {

    @TypeConverter
    fun toImportance(value: String) = enumValueOf<TodoItemImportance>(value)

    @TypeConverter
    fun fromImportance(value: TodoItemImportance) = value.name
}
