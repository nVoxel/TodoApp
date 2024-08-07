package com.voxeldev.todoapp.designsystem.components

import android.graphics.Picture
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

/**
 * @author nvoxel
 */
fun Modifier.copyContentImage(picture: Picture): Modifier = drawWithCache {
    val width = this.size.width.toInt()
    val height = this.size.height.toInt()

    onDrawWithContent {
        val pictureCanvas = Canvas(
            picture.beginRecording(width, height),
        )

        draw(this, this.layoutDirection, pictureCanvas, this.size) {
            this@onDrawWithContent.drawContent()
        }

        picture.endRecording()

        drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
    }
}
