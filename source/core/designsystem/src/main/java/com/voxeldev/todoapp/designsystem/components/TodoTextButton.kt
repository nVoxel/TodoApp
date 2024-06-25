package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTextButton(
    modifier: Modifier = Modifier,
    text: String,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false,
    ) {
        TextButton(
            modifier = modifier
                .defaultMinSize(minWidth = 10.dp),
            onClick = onClick,
            contentPadding = contentPadding,
        ) {
            Text(
                text = text.uppercase(),
                color = appPalette.colorBlue,
                style = AppTypography.button,
            )
        }
    }
}
