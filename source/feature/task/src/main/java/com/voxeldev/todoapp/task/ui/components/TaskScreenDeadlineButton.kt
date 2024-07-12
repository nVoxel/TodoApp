package com.voxeldev.todoapp.task.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.components.TodoDatePicker
import com.voxeldev.todoapp.designsystem.components.TodoSwitch
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.task.R

/**
 * @author nvoxel
 */
@Composable
internal fun TaskScreenDeadlineButton(
    datePickerDialogVisible: Boolean,
    deadlineTimestamp: Long?,
    deadlineTimestampString: String?,
    onChangeDatePickerDialogVisibility: (Boolean) -> Unit,
    onDeadlineTimestampChanged: (Long) -> Unit,
    onDeadlineTimestampReset: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChangeDatePickerDialogVisibility(true) }
            .padding(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.deadline),
                color = appPalette.labelPrimary,
                style = AppTypography.body,
            )

            deadlineTimestampString?.let {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    text = deadlineTimestampString,
                    color = appPalette.colorBlue,
                    style = AppTypography.subhead,
                )
            }
        }

        TodoSwitch(
            checked = deadlineTimestampString != null,
            onCheckedChange = {
                if (deadlineTimestampString == null) {
                    onChangeDatePickerDialogVisibility(true)
                } else {
                    onDeadlineTimestampReset()
                }
            },
        )

        TodoDatePicker(
            isVisible = datePickerDialogVisible,
            initialSelectedDateMillis = deadlineTimestamp?.let { it * 1000 } ?: System.currentTimeMillis(),
            confirmButtonText = stringResource(id = R.string.done),
            onConfirm = { updatedDeadlineTimestamp ->
                onDeadlineTimestampChanged(updatedDeadlineTimestamp)
                onChangeDatePickerDialogVisibility(false)
            },
            dismissButtonText = stringResource(id = R.string.cancel),
            onDismiss = { onChangeDatePickerDialogVisibility(false) },
        )
    }
}
