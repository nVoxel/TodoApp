package com.voxeldev.todoapp.repository.di

import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.database.di.LocalDataSourceModule
import com.voxeldev.todoapp.network.di.RemoteDataSourceModule
import com.voxeldev.todoapp.repository.DefaultTodoItemRepository
import com.voxeldev.todoapp.repository.utils.DataMerger
import com.voxeldev.todoapp.repository.utils.DefaultDataMerger
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import dagger.Binds
import dagger.Module

/**
 * @author nvoxel
 */
@Module(
    includes = [
        InternalRepositoryModule::class,
        LocalDataSourceModule::class,
        RemoteDataSourceModule::class,
    ],
)
interface RepositoryModule

@Module
internal interface InternalRepositoryModule {

    @Binds
    @AppScope
    fun bindTodoItemRepository(
        defaultTodoItemRepository: DefaultTodoItemRepository,
    ): TodoItemRepository

    @Binds
    fun bindDataMerger(
        defaultDataMerger: DefaultDataMerger,
    ): DataMerger
}
