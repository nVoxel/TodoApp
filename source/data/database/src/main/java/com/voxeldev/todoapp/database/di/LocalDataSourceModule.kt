package com.voxeldev.todoapp.database.di

import com.voxeldev.todoapp.database.todo.datasource.DefaultTodoItemLocalDataSource
import com.voxeldev.todoapp.database.todo.datasource.TodoItemLocalDataSource
import dagger.Binds
import dagger.Module

/**
 * @author nvoxel
 */
@Module(includes = [InternalLocalDataSourceModule::class, DatabaseModule::class])
interface LocalDataSourceModule

@Module
internal interface InternalLocalDataSourceModule {

    @Binds
    fun bindTodoItemLocalDataSource(
        defaultTodoItemLocalDataSource: DefaultTodoItemLocalDataSource,
    ): TodoItemLocalDataSource
}
