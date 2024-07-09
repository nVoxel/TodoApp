package com.voxeldev.todoapp.di.main

import android.content.Context
import com.voxeldev.todoapp.local.di.LocalModule
import com.voxeldev.todoapp.ui.activity.MainActivity
import com.voxeldev.todoapp.utils.di.UtilsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

/**
 * @author nvoxel
 */
@Component(modules = [LocalModule::class, UtilsModule::class])
@MainActivityScope
internal interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface MainActivityComponentFactory {
        fun create(@BindsInstance applicationContext: Context): MainActivityComponent
    }
}

@Scope
internal annotation class MainActivityScope
