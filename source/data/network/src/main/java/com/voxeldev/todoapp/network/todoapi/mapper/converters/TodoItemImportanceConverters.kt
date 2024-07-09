package com.voxeldev.todoapp.network.todoapi.mapper.converters

import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.utils.exceptions.InvalidFormatException

private const val IMPORTANCE_LOW = "low"
private const val IMPORTANCE_NORMAL = "basic"
private const val IMPORTANCE_URGENT = "important"

/**
 * @author nvoxel
 */
fun TodoItemImportance.toRequest(): String =
    when (this) {
        TodoItemImportance.Low -> IMPORTANCE_LOW
        TodoItemImportance.Normal -> IMPORTANCE_NORMAL
        TodoItemImportance.Urgent -> IMPORTANCE_URGENT
    }

fun String.toImportance(): TodoItemImportance =
    when (this) {
        IMPORTANCE_LOW -> TodoItemImportance.Low
        IMPORTANCE_NORMAL -> TodoItemImportance.Normal
        IMPORTANCE_URGENT -> TodoItemImportance.Urgent
        else -> throw InvalidFormatException()
    }
