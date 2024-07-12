package com.voxeldev.todoapp.task.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.task.R
import com.voxeldev.todoapp.task.ui.extensions.getDisplayColor
import com.voxeldev.todoapp.task.ui.extensions.getDisplayText

/**
 * @author nvoxel
 */
@Composable
internal fun TaskScreenImportanceButton(
    importance: TodoItemImportance,
    importanceDropdownVisible: Boolean,
    onImportanceChanged: (TodoItemImportance) -> Unit,
    onChangeImportanceDropdownVisibility: (Boolean) -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChangeImportanceDropdownVisibility(true) }
            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.importance),
            color = appPalette.labelPrimary,
            style = AppTypography.body,
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = importance.getDisplayText(),
            color = importance.getDisplayColor(forDropdown = false),
            style = AppTypography.subhead,
        )

        ImportanceDropdown(
            expanded = importanceDropdownVisible,
            onDismiss = { onChangeImportanceDropdownVisibility(false) },
            onImportanceClicked = { updatedImportance ->
                onImportanceChanged(updatedImportance)
                onChangeImportanceDropdownVisibility(false)
            },
        )
    }
}

@Composable
private fun ImportanceDropdown(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onImportanceClicked: (TodoItemImportance) -> Unit,
) {
    val appPalette = LocalAppPalette.current

    DropdownMenu(
        modifier = Modifier
            .defaultMinSize(minWidth = 164.dp)
            .background(color = appPalette.backElevated),
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        TodoItemImportance.entries.forEach { importance ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = importance.getDisplayText(),
                        color = importance.getDisplayColor(forDropdown = true),
                        style = AppTypography.body,
                    )
                },
                onClick = { onImportanceClicked(importance) },
            )
        }
    }
}
