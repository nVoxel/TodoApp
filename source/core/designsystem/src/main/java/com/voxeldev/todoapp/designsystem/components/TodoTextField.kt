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
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.preview.providers.TextPreviewParameterProvider
import com.voxeldev.todoapp.designsystem.theme.AppPalette
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

const val TODO_TEXT_FIELD_TAG = "todo_text_field"

/**
 * @author nvoxel
 */
@Composable
fun TodoTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    placeholderText: String? = null,
) {
    val appPalette = LocalAppPalette.current

    OutlinedTextField(
        modifier = modifier
            .testTag(tag = TODO_TEXT_FIELD_TAG)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 104.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(size = 8.dp),
            ),
        value = text,
        onValueChange = onTextChanged,
        colors = todoTextFieldColors(appPalette = appPalette),
        placeholder = {
            placeholderText?.let {
                PlaceholderText(placeholderText = placeholderText)
            }
        },
        textStyle = AppTypography.body,
        shape = RoundedCornerShape(size = 8.dp),
    )
}

@Composable
private fun PlaceholderText(placeholderText: String) {
    val appPalette = LocalAppPalette.current

    Text(
        text = placeholderText,
        color = appPalette.labelTertiary,
        style = AppTypography.body,
    )
}

@Composable
private fun todoTextFieldColors(appPalette: AppPalette): TextFieldColors =
    OutlinedTextFieldDefaults.colors(
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
    )

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
