package com.voxeldev.todoapp.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDatePicker(
    isVisible: Boolean,
    initialSelectedDateMillis: Long,
    confirmButtonText: String,
    onConfirm: (Long) -> Unit,
    dismissButtonText: String,
    onDismiss: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialSelectedDateMillis)

    if (isVisible) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = { datePickerState.selectedDateMillis?.let { onConfirm(it / 1000) } }) {
                    Text(
                        text = confirmButtonText.uppercase(),
                        color = appPalette.colorBlue,
                        style = AppTypography.button,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = dismissButtonText.uppercase(),
                        color = appPalette.colorBlue,
                        style = AppTypography.button,
                    )
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(selectedDayContainerColor = appPalette.colorBlue),
            )
        }
    }
}
