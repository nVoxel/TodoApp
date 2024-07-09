package com.voxeldev.todoapp.plugin.size

import org.gradle.api.provider.Property

/**
 * @author nvoxel
 */
interface ValidateApkSizeExtension {
    val validationEnabled: Property<Boolean>
    val maxApkSizeMegabytes: Property<Int>
}
