package com.voxeldev.todoapp.database.di

import android.content.Context
import androidx.room.Room
import com.voxeldev.todoapp.database.DB_FILE_NAME
import com.voxeldev.todoapp.database.TodoDatabase
import com.voxeldev.todoapp.database.todo.TodoDao
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
internal class DatabaseModule {

    @Provides
    @AppScope
    fun provideTodoDatabase(
        applicationContext: Context,
    ): TodoDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = TodoDatabase::class.java,
        name = DB_FILE_NAME,
    ).build()

    @Provides
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao =
        todoDatabase.todoDao()
}
