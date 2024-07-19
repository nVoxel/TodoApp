package com.voxeldev.todoapp.ui.navigation.containers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.TodoApp
import com.voxeldev.todoapp.task.di.DaggerTaskFeatureComponent
import com.voxeldev.todoapp.task.di.TaskScreenContainer

/**
 * @author nvoxel
 */
@Composable
fun rememberTaskScreenContainer(): TaskScreenContainer {
    val application = LocalContext.current.applicationContext as TodoApp
    return remember {
        TaskScreenContainer().also { container ->
            DaggerTaskFeatureComponent.factory()
                .create(dependencies = application.taskFeatureDependencies)
                .inject(taskScreenContainer = container)
        }
    }
}
