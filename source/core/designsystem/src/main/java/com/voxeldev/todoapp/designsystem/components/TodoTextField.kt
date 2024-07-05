package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.preview.providers.TextPreviewParameterProvider
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    placeholderText: String? = null,
) {
    val appPalette = LocalAppPalette.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 104.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(size = 8.dp),
            ),
        value = text,
        onValueChange = onTextChanged,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = appPalette.labelPrimary,
            focusedTextColor = appPalette.labelPrimary,
            unfocusedContainerColor = appPalette.backSecondary,
            focusedContainerColor = appPalette.backSecondary,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = appPalette.labelPrimary,
            selectionColors = TextSelectionColors(
                handleColor = appPalette.colorBlue,
                backgroundColor = appPalette.colorBlueSelection,
            ),
        ),
        placeholder = {
            placeholderText?.let {
                Text(
                    text = placeholderText,
                    color = appPalette.labelTertiary,
                    style = AppTypography.body,
                )
            }
        },
        textStyle = AppTypography.body,
        shape = RoundedCornerShape(size = 8.dp),
    )
}

@ComponentDayNightPreviews
@Composable
private fun Preview(
    @PreviewParameter(TextPreviewParameterProvider::class)
    text: String,
) {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoTextField(
                text = text,
                onTextChanged = {},
                placeholderText = "Text field with placeholder",
            )
        }
    }
}
