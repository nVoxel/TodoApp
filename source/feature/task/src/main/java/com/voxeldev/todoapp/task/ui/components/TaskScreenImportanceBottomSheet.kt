package com.voxeldev.todoapp.task.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.task.R
import com.voxeldev.todoapp.task.ui.extensions.getDisplayColor
import com.voxeldev.todoapp.task.ui.extensions.getDisplayText
import kotlinx.coroutines.launch

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskScreenImportanceBottomSheet(
    isVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onImportanceClicked: (TodoItemImportance) -> Unit,
) {
    val appPalette = LocalAppPalette.current
    val coroutineScope = rememberCoroutineScope()

    if (!isVisible) return

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = appPalette.backElevated,
        onDismissRequest = onDismiss,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
            text = stringResource(id = R.string.importance),
            color = appPalette.labelPrimary,
            style = AppTypography.largeTitle,
        )

        TodoItemImportance.entries.forEach { importance ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onImportanceClicked(importance)
                        coroutineScope
                            .launch {
                                sheetState.hide()
                            }
                            .invokeOnCompletion {
                                onDismiss()
                            }
                    }
                    .padding(horizontal = 32.dp, vertical = 16.dp),
            ) {
                val importanceContentDescription = importance.getDisplayText(forTranscribe = true)

                Text(
                    modifier = Modifier.semantics { contentDescription = importanceContentDescription },
                    text = importance.getDisplayText(),
                    color = importance.getDisplayColor(forDropdown = true),
                    style = AppTypography.body,
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
    }
}
