package com.voxeldev.todoapp.list.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.designsystem.components.TodoInfoDialog
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.list.R

/**
 * @author nvoxel
 */
@Composable
internal fun TodoItemInfoDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    todoItem: TodoItem,
    onRequestFormattedTimestamp: (Long) -> String,
) {
    val appPalette = LocalAppPalette.current

    val creationTimestamp = remember(todoItem) {
        onRequestFormattedTimestamp(todoItem.creationTimestamp)
    }
    val modifiedTimestamp = remember(todoItem) {
        todoItem.modifiedTimestamp?.let { modifiedTimestamp ->
            onRequestFormattedTimestamp(modifiedTimestamp)
        }
    }

    TodoInfoDialog(
        isVisible = isVisible,
        onDismiss = onDismiss,
        confirmButtonText = stringResource(id = R.string.done),
        onConfirmButtonClicked = onDismiss,
        titleContent = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(id = R.string.task_info),
                    tint = appPalette.labelSecondary,
                )

                Spacer(modifier = Modifier.width(width = 12.dp))

                Text(
                    text = stringResource(id = R.string.task_info),
                    color = appPalette.labelPrimary,
                    style = AppTypography.title,
                )
            }
        },
    ) {
        TableRow(
            titleColumnText = stringResource(id = R.string.task_id),
            contentColumnText = todoItem.id,
        )

        Spacer(modifier = Modifier.height(height = 12.dp))

        TableRow(
            titleColumnText = stringResource(id = R.string.creation_date),
            contentColumnText = creationTimestamp,
        )

        modifiedTimestamp?.let {
            Spacer(modifier = Modifier.height(height = 12.dp))

            TableRow(
                titleColumnText = stringResource(id = R.string.modification_date),
                contentColumnText = modifiedTimestamp,
            )
        }
    }
}

@Composable
private fun TableRow(
    titleColumnText: String,
    contentColumnText: String,
) {
    val appPalette = LocalAppPalette.current

    Row {
        Text(
            modifier = Modifier
                .weight(weight = 0.5f)
                .padding(end = 4.dp),
            text = titleColumnText,
            color = appPalette.labelPrimary,
            style = AppTypography.body,
        )

        Text(
            modifier = Modifier
                .weight(weight = 0.5f)
                .padding(start = 4.dp),
            text = contentColumnText,
            color = appPalette.labelSecondary,
            style = AppTypography.body,
        )
    }
}
