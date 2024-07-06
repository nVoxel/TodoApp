package com.voxeldev.todoapp.network.di

import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.network.todoapi.TodoItemRepositoryDefaultImpl
import com.voxeldev.todoapp.network.todoapi.TodoItemsListRepositoryDefaultImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Repository bindings module.
 * @author nvoxel
 */
@Module(includes = [InternalRepositoryModule::class, NetworkModule::class])
interface RepositoryModule

@Module
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
