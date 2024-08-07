package com.voxeldev.todoapp.di.navigation

import com.voxeldev.todoapp.ui.viewmodel.navigation.NavigationViewModelProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class NavigationContainer {
    @Inject lateinit var navigationViewModelProvider: NavigationViewModelProvider
}
