package com.voxeldev.todoapp.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import kotlin.math.sqrt

private const val REVEAL_CENTER = 0.5f

/**
 * @see <a href="https://gist.github.com/darvld/eb3844474baf2f3fc6d3ab44a4b4b5f8">Source</a>
 */
fun Modifier.circularReveal(
    transitionProgress: Float,
    revealFrom: Offset = Offset(REVEAL_CENTER, REVEAL_CENTER),
): Modifier {
    return drawWithCache {
        val path = Path()

        val center = revealFrom.mapTo(size = size)
        val radius = calculateRadius(
            normalizedOrigin = revealFrom,
            size = size,
        )

        path.addOval(
            Rect(
                center = center,
                radius = radius * transitionProgress,
            ),
        )

        onDrawWithContent {
            clipPath(path) { this@onDrawWithContent.drawContent() }
        }
    }
}

private fun Offset.mapTo(size: Size): Offset = Offset(x * size.width, y * size.height)

private fun calculateRadius(normalizedOrigin: Offset, size: Size) = with(normalizedOrigin) {
    val x = (if (x > REVEAL_CENTER) x else 1 - x) * size.width
    val y = (if (y > REVEAL_CENTER) y else 1 - y) * size.height

    sqrt(x * x + y * y)
}
