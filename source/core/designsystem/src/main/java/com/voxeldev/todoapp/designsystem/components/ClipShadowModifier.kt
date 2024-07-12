package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

/**
 * This modifier should be used to display shadow only at the bottom of the component
 * @author nvoxel
 */
fun Modifier.clipShadow() = clip(
    GenericShape { size, _ ->
    lineTo(size.width, 0f)
    lineTo(size.width, Float.MAX_VALUE)
    lineTo(0f, Float.MAX_VALUE)
},
)
