package com.voxeldev.todoapp.ui.navigation.containers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.TodoApp
import com.voxeldev.todoapp.list.di.DaggerListFeatureComponent
import com.voxeldev.todoapp.list.di.ListScreenContainer

/**
 * @author nvoxel
 */
@Composable
fun rememberListScreenContainer(): ListScreenContainer {
    val application = LocalContext.current.applicationContext as TodoApp
    return remember {
        ListScreenContainer().also { container ->
            DaggerListFeatureComponent.factory()
                .create(dependencies = application.listFeatureDependencies)
                .inject(listScreenContainer = container)
        }
    }
}
