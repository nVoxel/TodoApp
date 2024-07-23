package com.voxeldev.todoapp.ui.navigation.containers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.TodoApp
import com.voxeldev.todoapp.about.compose.DaggerAboutFeatureComponent
import com.voxeldev.todoapp.about.di.AboutScreenContainer

/**
* @author nvoxel
*/
@Composable
internal fun rememberAboutScreenContainer(): AboutScreenContainer {
    val application = LocalContext.current.applicationContext as TodoApp
    return remember {
        AboutScreenContainer().also { container ->
            DaggerAboutFeatureComponent.factory()
                .create(dependencies = application.aboutFeatureDependencies)
                .inject(aboutScreenContainer = container)
        }
    }
}
