package com.voxeldev.todoapp.initializer

import android.content.Context
import androidx.startup.Initializer

/**
 * Initializes dependency graph.
 * @author nvoxel
 */
internal class DependencyGraphInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
