package com.voxeldev.todoapp.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.components.TodoDatePicker
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.components.TodoSmallTopBar
import com.voxeldev.todoapp.designsystem.components.TodoSwitch
import com.voxeldev.todoapp.designsystem.components.TodoTextField
import com.voxeldev.todoapp.designsystem.components.conditional
import com.voxeldev.todoapp.designsystem.extensions.calculateTopBarElevation
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.task.R
import com.voxeldev.todoapp.task.ui.extensions.getDisplayColor
import com.voxeldev.todoapp.task.ui.extensions.getDisplayText
import com.voxeldev.todoapp.task.ui.preview.TaskScreenPreviewData
import com.voxeldev.todoapp.task.viewmodel.TaskViewModel

/**
 * @author nvoxel
 */
@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    onClose: () -> Unit,
) {
    val text by viewModel.text.collectAsStateWithLifecycle()
    val importance by viewModel.importance.collectAsStateWithLifecycle()
    val deadlineTimestamp by viewModel.deadlineTimestamp.collectAsStateWithLifecycle()
    val deadlineTimestampString by viewModel.deadlineTimestampString.collectAsStateWithLifecycle()
    val saveButtonActive by viewModel.saveButtonActive.collectAsStateWithLifecycle()

    BaseScreen(
        viewModel = viewModel,
        retryCallback = viewModel::getTodoItem,
    ) {
        TaskScreen(
            editTask = viewModel.taskId != null,
            text = text,
            importance = importance,
            deadlineTimestamp = deadlineTimestamp,
            deadlineTimestampString = deadlineTimestampString,
            saveButtonActive = saveButtonActive,
            onTextChanged = { updatedText -> viewModel.updateText(text = updatedText) },
            onImportanceChanged = { updatedImportance -> viewModel.updateImportance(importance = updatedImportance) },
            onDeadlineTimestampChanged = { updatedDeadlineTimestamp ->
                viewModel.updateDeadlineTimestamp(deadlineTimestamp = updatedDeadlineTimestamp)
            },
            onDeadlineTimestampReset = { viewModel.resetDeadlineTimestamp() },
            onSaveClicked = { viewModel.saveItem(onClose) },
            onDeleteClicked = { viewModel.deleteItem(onClose) },
            onCloseClicked = onClose,
        )
    }
}

@Composable
private fun TaskScreen(
    editTask: Boolean,
    text: String,
    importance: TodoItemImportance,
    deadlineTimestamp: Long?,
    deadlineTimestampString: String?,
    saveButtonActive: Boolean,
    onTextChanged: (String) -> Unit,
    onImportanceChanged: (TodoItemImportance) -> Unit,
    onDeadlineTimestampChanged: (Long) -> Unit,
    onDeadlineTimestampReset: () -> Unit,
    onSaveClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    val scrollState = rememberScrollState()

    var importanceDropdownVisible by rememberSaveable { mutableStateOf(false) }
    var datePickerDialogVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(shadowElevation = scrollState.calculateTopBarElevation()) {
                TodoSmallTopBar(
                    onCloseClicked = onCloseClicked,
                    displayButton = true,
                    buttonText = stringResource(id = if (editTask) R.string.save else R.string.create),
                    isButtonActive = saveButtonActive,
                    onButtonClicked = onSaveClicked,
                )
            }
        },
        containerColor = appPalette.backPrimary,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(state = scrollState),
        ) {
            Spacer(modifier = Modifier.height(height = 8.dp))

            TodoTextField(
                text = text,
                onTextChanged = onTextChanged,
                placeholderText = stringResource(id = R.string.todo_placeholder),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { importanceDropdownVisible = true }
                    .padding(top = 28.dp, bottom = 16.dp),
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
                    onDismiss = { importanceDropdownVisible = false },
                    onImportanceClicked = { updatedImportance ->
                        onImportanceChanged(updatedImportance)
                        importanceDropdownVisible = false
                    },
                )
            }

            TodoDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialogVisible = true }
                    .padding(top = 16.dp, bottom = 24.dp),
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
                            datePickerDialogVisible = true
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
                        datePickerDialogVisible = false
                    },
                    dismissButtonText = stringResource(id = R.string.cancel),
                    onDismiss = { datePickerDialogVisible = false },
                )
            }

            TodoDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .conditional(
                        condition = editTask,
                        conditionMetModifier = Modifier.clickable(onClick = onDeleteClicked),
                    )
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete),
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

@ScreenDayNightPreviews
@Composable
private fun TaskScreenPreview() {
    PreviewBase {
        TaskScreen(
            editTask = true,
            text = TaskScreenPreviewData.text,
            importance = TaskScreenPreviewData.importance,
            deadlineTimestamp = TaskScreenPreviewData.deadlineTimestamp,
            deadlineTimestampString = TaskScreenPreviewData.deadlineTimestampString,
            saveButtonActive = true,
            onTextChanged = {},
            onImportanceChanged = {},
            onDeadlineTimestampChanged = {},
            onDeadlineTimestampReset = {},
            onSaveClicked = {},
            onDeleteClicked = {},
            onCloseClicked = {},
        )
    }
}
