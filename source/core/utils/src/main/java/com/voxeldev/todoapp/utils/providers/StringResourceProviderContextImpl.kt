package com.voxeldev.todoapp.utils.providers

import android.content.Context
import com.voxeldev.todoapp.utils.R

/**
 * @author nvoxel
 */
internal class StringResourceProviderContextImpl(context: Context) : StringResourceProvider {

    private val resources = context.resources

    override fun getTodoApiBaseUrl(): String = resources.getString(R.string.todo_api_base_url)
}
