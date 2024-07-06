package com.voxeldev.todoapp.task.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.components.ErrorSnackbarEffect
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.components.TodoTextField
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.task.R
import com.voxeldev.todoapp.task.di.TaskScreenContainer
import com.voxeldev.todoapp.task.ui.components.TaskScreenDeadlineButton
import com.voxeldev.todoapp.task.ui.components.TaskScreenDeleteButton
import com.voxeldev.todoapp.task.ui.components.TaskScreenImportanceButton
import com.voxeldev.todoapp.task.ui.components.TaskScreenTopBar
import com.voxeldev.todoapp.task.ui.preview.TaskScreenPreviewData
import com.voxeldev.todoapp.task.viewmodel.TaskViewModel
import com.voxeldev.todoapp.utils.extensions.getDisplayMessage

/**
 * @author nvoxel
 */
@Composable
fun TaskScreen(
    taskId: String?,
    taskScreenContainer: TaskScreenContainer,
    viewModel: TaskViewModel = viewModel(
        factory = taskScreenContainer.taskViewModelProvider.create(taskId = taskId),
    ),
    onClose: () -> Unit,
) {
    val text by viewModel.text.collectAsStateWithLifecycle()
    val importance by viewModel.importance.collectAsStateWithLifecycle()
    val deadlineTimestamp by viewModel.deadlineTimestamp.collectAsStateWithLifecycle()
    val deadlineTimestampString by viewModel.deadlineTimestampString.collectAsStateWithLifecycle()
    val saveButtonActive by viewModel.saveButtonActive.collectAsStateWithLifecycle()
    val error by viewModel.exception.collectAsStateWithLifecycle()

    val displayFullscreenError = taskId != null && text.isBlank() // don't display after initial load

    BaseScreen(
        viewModel = viewModel,
        displayFullscreenError = displayFullscreenError,
        retryCallback = viewModel::getTodoItem,
    ) {
        TaskScreen(
            editTask = taskId != null,
            text = text,
            importance = importance,
            deadlineTimestamp = deadlineTimestamp,
            deadlineTimestampString = deadlineTimestampString,
            saveButtonActive = saveButtonActive,
            error = error?.getDisplayMessage(),
            onSnackbarHide = viewModel::onSnackbarHide,
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
    error: String?,
    onSnackbarHide: () -> Unit,
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
    val snackbarHostState = remember { SnackbarHostState() }

    var importanceDropdownVisible by rememberSaveable { mutableStateOf(false) }
    var datePickerDialogVisible by rememberSaveable { mutableStateOf(false) }

    ErrorSnackbarEffect(
        errorMessage = error,
        snackbarHostState = snackbarHostState,
        onHide = onSnackbarHide,
    )

    Scaffold(
        topBar = {
            TaskScreenTopBar(
                scrollState = scrollState,
                saveButtonActive = saveButtonActive,
                editTask = editTask,
                onSaveClicked = onSaveClicked,
                onCloseClicked = onCloseClicked,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = appPalette.backPrimary,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .verticalScroll(state = scrollState),
        ) {
            Spacer(modifier = Modifier.height(height = 8.dp))

            TodoTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .heightIn(max = 260.dp),
                text = text,
                onTextChanged = onTextChanged,
                placeholderText = stringResource(id = R.string.todo_placeholder),
            )

            Spacer(modifier = Modifier.height(height = 12.dp))

            TaskScreenImportanceButton(
                importance = importance,
                importanceDropdownVisible = importanceDropdownVisible,
                onImportanceChanged = onImportanceChanged,
                onChangeImportanceDropdownVisibility = { newImportanceDropdownVisible ->
                    importanceDropdownVisible = newImportanceDropdownVisible
                },
            )

            TodoDivider()

            TaskScreenDeadlineButton(
                datePickerDialogVisible = datePickerDialogVisible,
                deadlineTimestamp = deadlineTimestamp,
                deadlineTimestampString = deadlineTimestampString,
                onChangeDatePickerDialogVisibility = { newDatePickerDialogVisible ->
                    datePickerDialogVisible = newDatePickerDialogVisible
                },
                onDeadlineTimestampChanged = onDeadlineTimestampChanged,
                onDeadlineTimestampReset = onDeadlineTimestampReset,
            )

            TodoDivider()

            TaskScreenDeleteButton(
                editTask = editTask,
                onDeleteClicked = onDeleteClicked,
            )

            Spacer(modifier = Modifier.imePadding())
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
            error = null,
            onSnackbarHide = {},
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
