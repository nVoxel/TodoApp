package com.voxeldev.todoapp.utils.extensions

import android.content.Context
import android.content.pm.ApplicationInfo

/**
 * @author nvoxel
 */
fun Context.isDebuggable() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
