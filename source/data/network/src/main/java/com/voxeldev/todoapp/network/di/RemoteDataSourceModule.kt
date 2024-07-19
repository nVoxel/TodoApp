package com.voxeldev.todoapp.network.di

import com.voxeldev.todoapp.network.todoapi.DefaultTodoItemListRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.DefaultTodoItemRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.TodoItemListRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.TodoItemRemoteDataSource
import dagger.Binds
import dagger.Module

/**
 * Data source bindings module.
 * @author nvoxel
 */
@Module(includes = [InternalRemoteDataSourceModule::class, NetworkModule::class])
interface RemoteDataSourceModule

@Module
internal interface InternalRemoteDataSourceModule {

    @Binds
    fun bindTodoItemListRemoteDataSource(
        defaultTodoItemListRemoteDataSource: DefaultTodoItemListRemoteDataSource,
    ): TodoItemListRemoteDataSource

    @Binds
    fun bindTodoItemRemoteDataSource(
        defaultTodoItemRemoteDataSource: DefaultTodoItemRemoteDataSource,
    ): TodoItemRemoteDataSource
}
