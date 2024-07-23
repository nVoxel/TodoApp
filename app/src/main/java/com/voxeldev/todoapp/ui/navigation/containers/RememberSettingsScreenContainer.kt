package com.voxeldev.todoapp.ui.navigation.containers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.TodoApp
import com.voxeldev.todoapp.settings.di.DaggerSettingsFeatureComponent
import com.voxeldev.todoapp.settings.di.SettingsScreenContainer

/**
 * @author nvoxel
 */
@Composable
internal fun rememberSettingsScreenContainer(): SettingsScreenContainer {
    val application = LocalContext.current.applicationContext as TodoApp
    return remember {
        SettingsScreenContainer().also { container ->
            DaggerSettingsFeatureComponent.factory()
                .create(dependencies = application.settingsFeatureDependencies)
                .inject(settingsScreenContainer = container)
        }
    }
}
