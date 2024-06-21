package com.voxeldev.todoapp.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author nvoxel
 */
fun Long.formatTimestamp(format: SimpleDateFormat): String {
    val date = Date(this * 1000)
    return format.format(date)
}
