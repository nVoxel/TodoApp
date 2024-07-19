package com.voxeldev.todoapp.list.di

import androidx.compose.runtime.Stable
import com.voxeldev.todoapp.list.viewmodel.ListViewModelProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
@Stable
class ListScreenContainer {
    @Inject lateinit var listViewModelProvider: ListViewModelProvider
}
