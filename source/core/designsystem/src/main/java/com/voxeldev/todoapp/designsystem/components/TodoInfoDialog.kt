package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.AppTypography

/**
 * @author nvoxel
 */
@Composable
fun TodoInfoDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    confirmButtonText: String? = null,
    onConfirmButtonClicked: () -> Unit = {},
    titleContent: @Composable () -> Unit? = {},
    dialogContent: @Composable ColumnScope.() -> Unit,
) {
    if (isVisible) {
        Dialog(onDismissRequest = onDismiss) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Column(modifier = Modifier.padding(all = 24.dp)) {
                        titleContent()

                        Spacer(modifier = Modifier.height(height = 24.dp))

                        dialogContent()
                    }

                    if (confirmButtonText == null) return@Column

                    Footer(
                        confirmButtonText = confirmButtonText,
                        onConfirmButtonClicked = onConfirmButtonClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun Footer(
    confirmButtonText: String,
    onConfirmButtonClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        TodoTextButton(
            text = confirmButtonText,
            onClick = onConfirmButtonClicked,
            contentPadding = PaddingValues(all = 12.dp),
        )
    }
}

@ComponentDayNightPreviews
@Composable
private fun Preview() {
    PreviewBase {
        TodoInfoDialog(
            isVisible = true,
            onDismiss = {},
            confirmButtonText = "Done",
            onConfirmButtonClicked = {},
            titleContent = {
                Text(
                    text = "Dialog Title",
                    style = AppTypography.title,
                )
            },
        ) {
            repeat(times = 5) {
                Text(text = "Dialog content")
            }
        }
    }
}
