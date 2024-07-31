package com.voxeldev.todoapp.utils.providers

import android.content.Context
import com.voxeldev.todoapp.utils.R

/**
 * Provides custom strings.
 * @author nvoxel
 */
class StringResourceProviderContextImpl(context: Context) : StringResourceProvider {

    private val resources = context.resources

    override fun getVersionName(): String = resources.getString(R.string.version_name)

    override fun getTodoApiBaseUrl(): String = resources.getString(R.string.todo_api_base_url)

    override fun getGitHubProfileUrl(): String = resources.getString(R.string.github_profile_url)

    override fun getGitHubRepoUrl(): String = resources.getString(R.string.github_repo_url)
}
