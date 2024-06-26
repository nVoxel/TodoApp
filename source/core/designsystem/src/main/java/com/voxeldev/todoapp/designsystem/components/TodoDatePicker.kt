package com.voxeldev.todoapp.designsystem.components

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
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
    val appPalette = LocalAppPalette.current

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialSelectedDateMillis)

    // Fixes DatePicker not fully visible in landscape orientation
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) datePickerState.displayMode = DisplayMode.Input

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
                showModeToggle = !isLandscape,
            )
        }
    }
}

@Preview(name = "Light Portrait", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Portrait", uiMode = Configuration.UI_MODE_NIGHT_YES)
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
