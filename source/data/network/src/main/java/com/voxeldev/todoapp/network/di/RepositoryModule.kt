package com.voxeldev.todoapp.network.di

import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.network.todoapi.TodoItemRepositoryDefaultImpl
import com.voxeldev.todoapp.network.todoapi.TodoItemsListRepositoryDefaultImpl
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import dagger.Binds
import dagger.Module

/**
 * Repository bindings module.
 * @author nvoxel
 */
@Module(includes = [InternalRepositoryModule::class, NetworkModule::class])
interface RepositoryModule

@Module
internal interface InternalRepositoryModule {

    @Binds
    @AppScope
    fun bindTodoItemListRepository(
        todoItemsListRepositoryDefaultImpl: TodoItemsListRepositoryDefaultImpl,
    ): TodoItemListRepository

    @Binds
    @AppScope
    fun bindTodoItemRepository(
        todoItemRepositoryDefaultImpl: TodoItemRepositoryDefaultImpl,
    ): TodoItemRepository
}
