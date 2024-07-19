package com.voxeldev.todoapp.utils.extensions

import com.voxeldev.todoapp.utils.DOUBLE_FORMAT
import java.util.Locale

private const val BYTES_IN_KB = 1024.0
private const val BYTES_IN_MB = 1_048_576.0

/**
 * @author nvoxel
 */
fun Long.convertSizeBytes(): String =
    if (this > BYTES_IN_MB) {
        "${formatDouble(bytesToMegabytes())} MB"
    } else if (this > BYTES_IN_KB) {
        "${formatDouble(bytesToKilobytes())} KB"
    } else {
        "$this bytes"
    }

fun Long.bytesToMegabytes(): Double = this / BYTES_IN_MB

fun Long.bytesToKilobytes(): Double = this / BYTES_IN_KB

private fun formatDouble(double: Double): String = String.format(Locale.getDefault(), DOUBLE_FORMAT, double)
