package com.voxeldev.todoapp.plugin.stats

import org.gradle.api.provider.Property

/**
 * @author nvoxel
 */
interface TelegramFileExtension {
    val sendFile: Property<Boolean>
    val chatId: Property<String>
    val token: Property<String>
}
