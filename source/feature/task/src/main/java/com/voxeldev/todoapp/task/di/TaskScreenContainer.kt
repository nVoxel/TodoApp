package com.voxeldev.todoapp.task.di

import androidx.compose.runtime.Stable
import com.voxeldev.todoapp.task.viewmodel.TaskViewModelProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
@Stable
class TaskScreenContainer {
    @Inject lateinit var taskViewModelProvider: TaskViewModelProvider.Factory
}
