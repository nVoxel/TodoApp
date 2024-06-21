package com.voxeldev.todoapp.network.di

import com.voxeldev.todoapp.api.repository.TodoItemsRepository
import com.voxeldev.todoapp.network.TodoItemsRepositoryStubImpl
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
    fun bindTodoItems(
        todoItemsRepositoryStubImpl: TodoItemsRepositoryStubImpl,
    ): TodoItemsRepository
}
