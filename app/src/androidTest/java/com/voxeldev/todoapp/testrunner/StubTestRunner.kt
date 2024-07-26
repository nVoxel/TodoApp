package com.voxeldev.todoapp.testrunner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.voxeldev.todoapp.TestTodoApp

/**
 * @author nvoxel
 */
class StubTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestTodoApp::class.java.name, context)
    }
}
