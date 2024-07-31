package com.voxeldev.todoapp.network.utils

import kotlin.test.fail

/**
 * @author nvoxel
 */
internal fun loadResourceAsString(clazz: Any, fileName: String): String {
    val jsonFile = clazz.javaClass.classLoader?.getResourceAsStream(fileName)
        ?: fail(message = "Failed to load test resource")
    return jsonFile.bufferedReader().use { it.readText() }
}
