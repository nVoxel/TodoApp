package com.voxeldev.todoapp.ui.navigation.containers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.TodoApp
import com.voxeldev.todoapp.auth.di.AuthScreenContainer
import com.voxeldev.todoapp.auth.di.DaggerAuthFeatureComponent

/**
 * @author nvoxel
 */
@Composable
fun rememberAuthScreenContainer(): AuthScreenContainer {
    val application = LocalContext.current.applicationContext as TodoApp
    return remember {
        AuthScreenContainer().also { container ->
            DaggerAuthFeatureComponent.factory()
                .create(dependencies = application.authFeatureDependencies)
                .inject(authScreenContainer = container)
        }
    }
}
