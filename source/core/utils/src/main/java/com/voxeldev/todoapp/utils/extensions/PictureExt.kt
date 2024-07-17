package com.voxeldev.todoapp.utils.extensions

import android.graphics.Bitmap
import android.graphics.Picture

/**
 * @author nvoxel
 */
fun Picture.copyToBitmap(bitmap: Bitmap) {
    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawPicture(this)
}
