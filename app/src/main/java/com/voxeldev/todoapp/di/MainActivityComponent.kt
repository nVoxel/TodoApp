package com.voxeldev.todoapp.di

import com.voxeldev.todoapp.local.di.LocalModule
import com.voxeldev.todoapp.ui.activity.MainActivity
import dagger.Component

/**
 * @author nvoxel
 */
@Component(modules = [LocalModule::class])
interface MainActivityComponent {

    fun inject(activity: MainActivity)
}

@Component.Factory
interface MainActivityComponentFactory {
    fun create(): MainActivityComponent
}
