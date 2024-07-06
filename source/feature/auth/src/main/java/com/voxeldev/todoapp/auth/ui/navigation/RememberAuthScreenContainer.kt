package com.voxeldev.todoapp.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.auth.di.AuthScreenContainer
import com.voxeldev.todoapp.auth.di.DaggerAuthFeatureComponent

/**
 * @author nvoxel
 */
@Composable
fun rememberAuthScreenContainer(): AuthScreenContainer {
    val applicationContext = LocalContext.current.applicationContext
    return remember {
        AuthScreenContainer().also { container ->
            DaggerAuthFeatureComponent.factory()
                .create(applicationContext = applicationContext)
                .inject(authScreenContainer = container)
        }
    }
}
