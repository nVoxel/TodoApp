package com.voxeldev.todoapp.network.todoapi.datasource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Task data that can be send to the backend.
 * @author nvoxel
 */
@Serializable
data class TodoItemData(
    val id: String,
    val text: String,
    val importance: String,
    @SerialName("deadline") val deadlineTimestamp: Long? = null,
    @SerialName("done") val isComplete: Boolean,
    val color: String? = null,
    @SerialName("created_at") val creationTimestamp: Long,
    @SerialName("changed_at") val modifiedTimestamp: Long,
    @SerialName("last_updated_by") val lastUpdatedBy: String,
)
