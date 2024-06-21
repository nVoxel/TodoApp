package com.voxeldev.todoapp.designsystem.components

import androidx.compose.ui.Modifier

/**
 * @author nvoxel
 */
fun Modifier.conditional(condition: Boolean, conditionMetModifier: Modifier) =
    if (condition) then(other = conditionMetModifier) else this
