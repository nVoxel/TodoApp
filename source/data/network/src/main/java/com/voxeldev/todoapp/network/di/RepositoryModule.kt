package com.voxeldev.todoapp.network.di

import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.network.todoapi.TodoItemRepositoryDefaultImpl
import com.voxeldev.todoapp.network.todoapi.TodoItemsListRepositoryDefaultImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Module(includes = [InternalRepositoryModule::class])
@InstallIn(SingletonComponent::class)
interface RepositoryModule

@Module
@InstallIn(SingletonComponent::class)
internal interface InternalRepositoryModule {

    @Binds
    @Singleton
    fun bindTodoItemListRepository(
        todoItemsListRepositoryDefaultImpl: TodoItemsListRepositoryDefaultImpl,
    ): TodoItemListRepository

    @Binds
    @Singleton
    fun bindTodoItemRepository(
        todoItemRepositoryDefaultImpl: TodoItemRepositoryDefaultImpl,
    ): TodoItemRepository
}
