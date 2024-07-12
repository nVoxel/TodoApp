package com.voxeldev.todoapp.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.components.TodoCheckbox
import com.voxeldev.todoapp.designsystem.icons.AdditionalIcons
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.list.R

/**
 * @author nvoxel
 */
@Composable
internal fun ListItem(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
    onClicked: (String) -> Unit,
    onCheckClicked: (String, Boolean) -> Unit,
    onRequestFormattedTimestamp: (Long) -> String,
) {
    val appPalette = LocalAppPalette.current

    var isInfoDialogVisible by rememberSaveable { mutableStateOf(false) }

    val deadlineTimestamp = remember(todoItem) {
        todoItem.deadlineTimestamp?.let { deadlineTimestamp ->
            onRequestFormattedTimestamp(deadlineTimestamp)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = appPalette.backSecondary)
            .clickable { onClicked(todoItem.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        TodoCheckbox(
            isChecked = todoItem.isComplete,
            onCheckedChange = { checked -> onCheckClicked(todoItem.id, checked) },
            isImportant = todoItem.importance == TodoItemImportance.Urgent,
        )

        Spacer(modifier = Modifier.width(width = 12.dp))

        ListItemOverview(
            todoItem = todoItem,
            deadlineTimestamp = deadlineTimestamp,
        )

        Spacer(modifier = Modifier.width(width = 12.dp))

        Icon(
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable { isInfoDialogVisible = true },
            imageVector = Icons.Outlined.Info,
            contentDescription = stringResource(id = R.string.info),
            tint = appPalette.labelTertiary,
        )
    }

    TodoItemInfoDialog(
        isVisible = isInfoDialogVisible,
        onDismiss = { isInfoDialogVisible = false },
        todoItem = todoItem,
        onRequestFormattedTimestamp = onRequestFormattedTimestamp,
    )
}

@Composable
private fun RowScope.ListItemOverview(
    todoItem: TodoItem,
    deadlineTimestamp: String?,
) {
    val appPalette = LocalAppPalette.current

    Row(
        modifier = Modifier
            .weight(weight = 1f),
    ) {
        if (todoItem.importance != TodoItemImportance.Normal && !todoItem.isComplete) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = if (todoItem.importance == TodoItemImportance.Urgent) {
                    AdditionalIcons.ImportanceHigh
                } else {
                    AdditionalIcons.ImportanceLow
                },
                contentDescription = stringResource(id = R.string.importance),
                tint = if (todoItem.importance == TodoItemImportance.Urgent) appPalette.colorRed else appPalette.colorGray,
            )
        }

        Column {
            Text(
                text = todoItem.text,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = if (todoItem.isComplete) appPalette.labelTertiary else appPalette.labelPrimary,
                style = if (todoItem.isComplete) AppTypography.bodyStrikethrough else AppTypography.body,
            )

            deadlineTimestamp?.let {
                Spacer(modifier = Modifier.height(height = 4.dp))

                Text(
                    text = deadlineTimestamp,
                    color = appPalette.labelTertiary,
                    style = AppTypography.subhead,
                )
            }
        }
    }
}
