package com.voxeldev.todoapp

import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.di.app.AppComponent
import com.voxeldev.todoapp.di.app.DaggerTestAppComponent
import com.voxeldev.todoapp.di.app.TestAppComponent
import com.voxeldev.todoapp.utils.extensions.lazyUnsafe
import javax.inject.Inject

/**
 * @author nvoxel
 */
class TestTodoApp : TodoApp() {

    @Inject
    lateinit var todoItemRepository: TodoItemRepository

    private val testAppComponent: TestAppComponent by lazyUnsafe {
        DaggerTestAppComponent.factory().create(applicationContext = applicationContext)
    }

    override val appComponent: AppComponent by lazyUnsafe { testAppComponent }

    override fun onCreate() {
        super.onCreate()
        testAppComponent.inject(testTodoApp = this)
    }
}
