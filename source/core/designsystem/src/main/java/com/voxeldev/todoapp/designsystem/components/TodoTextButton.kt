package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.preview.providers.BooleanPreviewParameterProvider
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
    enabled: Boolean = true,
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
            colors = ButtonDefaults.textButtonColors(
                contentColor = appPalette.colorBlue,
            ),
            contentPadding = contentPadding,
            enabled = enabled,
        ) {
            Text(
                text = text.uppercase(),
                style = AppTypography.button,
            )
        }
    }
}

@ComponentDayNightPreviews
@Composable
private fun Preview(
    @PreviewParameter(BooleanPreviewParameterProvider::class)
    enabled: Boolean,
) {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoTextButton(
                text = "Text Button",
                enabled = enabled,
                onClick = {},
            )
        }
    }
}
