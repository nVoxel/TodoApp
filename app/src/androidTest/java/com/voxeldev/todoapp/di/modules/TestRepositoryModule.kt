package com.voxeldev.todoapp.di.modules

import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.di.stubs.repository.StubTodoItemRepository
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import dagger.Binds
import dagger.Module

/**
 * @author nvoxel
 */
@Module
interface TestRepositoryModule {

    @Binds
    @AppScope
    fun bindTodoItemRepository(
        stubTodoItemRepository: StubTodoItemRepository,
    ): TodoItemRepository
}
