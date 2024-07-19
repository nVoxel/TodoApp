package com.voxeldev.todoapp.about.di

import androidx.compose.runtime.Stable
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
@Stable
class AboutScreenContainer {
    @Inject lateinit var stringResourceProvider: StringResourceProvider
}
