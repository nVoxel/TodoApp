package com.voxeldev.todoapp.settings.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.settings.di.DaggerSettingsFeatureComponent
import com.voxeldev.todoapp.settings.di.SettingsScreenContainer

/**
 * @author nvoxel
 */
@Composable
fun rememberSettingsScreenContainer(): SettingsScreenContainer {
    val applicationContext = LocalContext.current.applicationContext
    return remember {
        SettingsScreenContainer().also { container ->
            DaggerSettingsFeatureComponent.factory()
                .create(applicationContext = applicationContext)
                .inject(settingsScreenContainer = container)
        }
    }
}
