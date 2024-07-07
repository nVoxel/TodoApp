package com.voxeldev.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.di.navigation.DaggerNavigationComponent
import com.voxeldev.todoapp.di.navigation.NavigationContainer

/**
 * @author nvoxel
 */
@Composable
fun rememberNavigationContainer(): NavigationContainer {
    val applicationContext = LocalContext.current.applicationContext
    return remember {
        NavigationContainer().also { container ->
            DaggerNavigationComponent.factory()
                .create(applicationContext = applicationContext)
                .inject(navigationContainer = container)
        }
    }
}
