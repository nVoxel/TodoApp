package com.voxeldev.todoapp.designsystem.components

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
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

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoTextField(
                text = "Text field that contains some text",
                onTextChanged = {},
            )
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewPlaceholder() {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoTextField(
                text = "",
                onTextChanged = {},
                placeholderText = "Text field with placeholder",
            )
        }
    }
}
