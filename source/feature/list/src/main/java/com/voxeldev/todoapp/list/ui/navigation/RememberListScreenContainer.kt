package com.voxeldev.todoapp.list.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.list.di.DaggerListFeatureComponent
import com.voxeldev.todoapp.list.di.ListScreenContainer

/**
 * @author nvoxel
 */
@Composable
fun rememberListScreenContainer(): ListScreenContainer {
    val applicationContext = LocalContext.current.applicationContext
    return remember {
        ListScreenContainer().also { container ->
            DaggerListFeatureComponent.factory()
                .create(applicationContext = applicationContext)
                .inject(listScreenContainer = container)
        }
    }
}
