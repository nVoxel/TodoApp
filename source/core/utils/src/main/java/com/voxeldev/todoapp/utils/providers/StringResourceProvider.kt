package com.voxeldev.todoapp.utils.providers

/**
 * Provides custom strings.
 * @author nvoxel
 */
interface StringResourceProvider {

    fun getVersionName(): String

    fun getTodoApiBaseUrl(): String

    fun getGitHubProfileUrl(): String

    fun getGetHubRepoUrl(): String
}
