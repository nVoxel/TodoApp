package com.voxeldev.todoapp.task.di

import dagger.Component
import javax.inject.Scope

/**
 * @author nvoxel
 */
@Component(dependencies = [TaskFeatureDependencies::class])
@TaskFeatureScope
interface TaskFeatureComponent {

    fun inject(taskScreenContainer: TaskScreenContainer)

    @Component.Factory
    interface TaskFeatureComponentFactory {
        fun create(dependencies: TaskFeatureDependencies): TaskFeatureComponent
    }
}

@Scope
annotation class TaskFeatureScope
