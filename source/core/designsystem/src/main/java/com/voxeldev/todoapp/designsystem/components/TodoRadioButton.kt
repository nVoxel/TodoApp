package com.voxeldev.todoapp.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoRadioButton(
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = appPalette.labelSecondary,
            ),
        )
    }
}
