package com.voxeldev.todoapp.di

import com.voxeldev.todoapp.auth.viewmodel.AuthViewModelProvider
import com.voxeldev.todoapp.list.viewmodel.ListViewModelProvider
import com.voxeldev.todoapp.settings.viewmodel.SettingsViewModelProvider
import com.voxeldev.todoapp.task.viewmodel.TaskViewModelProvider
import com.voxeldev.todoapp.ui.viewmodel.navigation.NavigationViewModelProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class ViewModelProviders @Inject constructor(
    val navigationViewModelProvider: NavigationViewModelProvider,
    val authViewModelProviderFactory: AuthViewModelProvider.Factory,
    val listViewModelProvider: ListViewModelProvider,
    val settingsViewModelProvider: SettingsViewModelProvider,
    val taskViewModelProviderFactory: TaskViewModelProvider.Factory,
)
