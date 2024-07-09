package com.voxeldev.todoapp.plugin.size

import org.gradle.api.provider.Property

/**
 * @author nvoxel
 */
interface ValidateApkSizeExtension {
    val chatId: Property<String>
    val token: Property<String>
    val validationEnabled: Property<Boolean>
    val maxApkSizeMegabytes: Property<Int>
}
