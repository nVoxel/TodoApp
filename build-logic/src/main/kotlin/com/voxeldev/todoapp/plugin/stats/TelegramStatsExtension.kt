package com.voxeldev.todoapp.plugin.stats

import org.gradle.api.provider.Property

/**
 * @author nvoxel
 */
interface TelegramStatsExtension {
    val chatId: Property<String>
    val token: Property<String>
}
