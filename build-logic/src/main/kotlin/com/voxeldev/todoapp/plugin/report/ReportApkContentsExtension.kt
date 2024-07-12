package com.voxeldev.todoapp.plugin.report

import org.gradle.api.provider.Property

/**
 * @author nvoxel
 */
interface ReportApkContentsExtension {
    val reportEnabled: Property<Boolean>
}
