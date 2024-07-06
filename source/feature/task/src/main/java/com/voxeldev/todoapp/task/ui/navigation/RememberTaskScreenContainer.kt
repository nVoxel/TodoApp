package com.voxeldev.todoapp.task.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.task.di.DaggerTaskFeatureComponent
import com.voxeldev.todoapp.task.di.TaskScreenContainer

/**
 * @author nvoxel
 */
@Composable
fun rememberTaskScreenContainer(): TaskScreenContainer {
    val applicationContext = LocalContext.current.applicationContext
    return remember {
        TaskScreenContainer().also { container ->
            DaggerTaskFeatureComponent.factory()
                .create(applicationContext = applicationContext)
                .inject(taskScreenContainer = container)
        }
    }
}
