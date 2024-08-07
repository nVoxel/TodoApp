package com.voxeldev.todoapp.task.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.designsystem.theme.ripple.DangerRippleTheme
import com.voxeldev.todoapp.task.R

/**
 * @author nvoxel
 */
@Composable
internal fun TaskScreenDeleteButton(
    editTask: Boolean,
    onDeleteClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    CompositionLocalProvider(LocalRippleTheme provides DangerRippleTheme) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = editTask,
                    onClickLabel = "Удалить задачу",
                    onClick = onDeleteClicked,
                )
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = if (editTask) appPalette.colorRed else appPalette.labelDisable,
            )

            Spacer(modifier = Modifier.width(width = 12.dp))

            Text(
                text = stringResource(id = R.string.delete),
                color = if (editTask) appPalette.colorRed else appPalette.labelDisable,
                style = AppTypography.body,
            )
        }
    }
}
