package com.voxeldev.todoapp.auth.ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun AuthTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    placeholderText: String? = null,
    enabled: Boolean = true,
    secure: Boolean = false,
    onDoneClicked: () -> Unit = {},
) {
    val appPalette = LocalAppPalette.current
    var textVisible by rememberSaveable { mutableStateOf(!secure) }

    OutlinedTextField(
        modifier = modifier
            .defaultMinSize(minHeight = 64.dp),
        value = text,
        onValueChange = onTextChanged,
        placeholder = {
            placeholderText?.let {
                Text(
                    text = placeholderText,
                    color = appPalette.labelSupport,
                    style = AppTypography.primaryTextField,
                )
            }
        },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = if (secure) KeyboardType.Password else KeyboardType.Text,
            imeAction = if (secure) ImeAction.Done else ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(onDone = { onDoneClicked() }),
        shape = RoundedCornerShape(size = 24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = appPalette.supportSeparator,
            focusedBorderColor = appPalette.backContrast,
            unfocusedTextColor = appPalette.labelPrimary,
            focusedTextColor = appPalette.labelPrimary,
        ),
        singleLine = true,
        textStyle = AppTypography.primaryTextField,
        visualTransformation = if (!textVisible) PasswordVisualTransformation('\u2217') else VisualTransformation.None,
        trailingIcon = {
            if (secure) {
                IconButton(onClick = { textVisible = !textVisible }) {
                    Icon(
                        imageVector = if (textVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = stringResource(id = R.string.password_visibility),
                        tint = appPalette.labelSecondary,
                    )
                }
            }
        },
    )
}
