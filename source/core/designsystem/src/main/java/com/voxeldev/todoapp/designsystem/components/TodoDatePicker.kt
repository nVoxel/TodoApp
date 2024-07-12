package com.voxeldev.todoapp.designsystem.components

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
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
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialSelectedDateMillis)

    // Fixes DatePicker not fully visible in landscape orientation
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) datePickerState.displayMode = DisplayMode.Input

    if (isVisible) {
        TodoDatePickerDialog(
            datePickerState = datePickerState,
            isLandscape = isLandscape,
            confirmButtonText = confirmButtonText,
            onConfirm = onConfirm,
            dismissButtonText = dismissButtonText,
            onDismiss = onDismiss,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoDatePickerDialog(
    datePickerState: DatePickerState,
    isLandscape: Boolean,
    confirmButtonText: String,
    onConfirm: (Long) -> Unit,
    dismissButtonText: String,
    onDismiss: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TodoDatePickerButton(
                text = confirmButtonText,
                onClick = { datePickerState.selectedDateMillis?.let { onConfirm(it / 1000) } },
            )
        },
        dismissButton = {
            TodoDatePickerButton(
                text = dismissButtonText,
                onClick = onDismiss,
            )
        },
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(selectedDayContainerColor = appPalette.colorBlue),
            showModeToggle = !isLandscape,
        )
    }
}

@Composable
private fun TodoDatePickerButton(
    text: String,
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    TextButton(onClick = onClick) {
        Text(
            text = text.uppercase(),
            color = appPalette.colorBlue,
            style = AppTypography.button,
        )
    }
}

@ComponentDayNightPreviews
@Preview(
    name = "Light Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = "spec:parent=pixel_8_pro,orientation=landscape",
)
@Preview(
    name = "Dark Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:parent=pixel_8_pro,orientation=landscape",
)
@Composable
private fun Preview() {
    PreviewBase {
        TodoDatePicker(
            isVisible = true,
            initialSelectedDateMillis = 1_000_000_000_000,
            confirmButtonText = "done",
            onConfirm = {},
            dismissButtonText = "cancel",
            onDismiss = {},
        )
    }
}
