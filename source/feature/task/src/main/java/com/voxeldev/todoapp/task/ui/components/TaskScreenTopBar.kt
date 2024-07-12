package com.voxeldev.todoapp.task.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.designsystem.components.TodoSmallTopBar
import com.voxeldev.todoapp.designsystem.components.clipShadow
import com.voxeldev.todoapp.designsystem.extensions.calculateTopBarElevation
import com.voxeldev.todoapp.task.R

/**
 * @author nvoxel
 */
@Composable
internal fun TaskScreenTopBar(
    scrollState: ScrollState,
    saveButtonActive: Boolean,
    editTask: Boolean,
    onSaveClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .clipShadow(),
        shadowElevation = scrollState.calculateTopBarElevation(),
    ) {
        TodoSmallTopBar(
            onCloseClicked = onCloseClicked,
            displayButton = true,
            buttonText = stringResource(id = if (editTask) R.string.save else R.string.create),
            isButtonActive = saveButtonActive,
            onButtonClicked = onSaveClicked,
        )
    }
}
