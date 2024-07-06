package com.voxeldev.todoapp.settings.di

import androidx.compose.runtime.Stable
import com.voxeldev.todoapp.settings.viewmodel.SettingsViewModelProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
@Stable
class SettingsScreenContainer {
    @Inject lateinit var settingsViewModelProvider: SettingsViewModelProvider
}
