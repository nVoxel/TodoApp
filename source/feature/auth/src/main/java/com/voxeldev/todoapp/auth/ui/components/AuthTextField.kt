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
import androidx.compose.material3.TextFieldColors
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
import com.voxeldev.todoapp.designsystem.theme.AppPalette
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
        modifier = modifier.defaultMinSize(minHeight = 64.dp),
        value = text,
        onValueChange = onTextChanged,
        placeholder = { PlaceholderText(placeholderText = placeholderText) },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = if (secure) KeyboardType.Password else KeyboardType.Text,
            imeAction = if (secure) ImeAction.Done else ImeAction.Next, // implied that password field is the last field in the form
        ),
        keyboardActions = KeyboardActions(onDone = { onDoneClicked() }),
        shape = RoundedCornerShape(size = 24.dp),
        colors = authTextFieldColors(appPalette = appPalette),
        singleLine = true,
        textStyle = AppTypography.primaryTextField,
        visualTransformation = if (!textVisible) PasswordVisualTransformation('\u2217') else VisualTransformation.None,
        trailingIcon = {
            TrailingIcon(
                isVisible = secure,
                textVisible = textVisible,
                onClick = { textVisible = !textVisible },
            )
        },
    )
}

@Composable
private fun PlaceholderText(placeholderText: String?) {
    val appPalette = LocalAppPalette.current

    placeholderText?.let {
        Text(
            text = placeholderText,
            color = appPalette.labelSupport,
            style = AppTypography.primaryTextField,
        )
    }
}

@Composable
private fun TrailingIcon(
    isVisible: Boolean,
    textVisible: Boolean,
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    if (isVisible) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (textVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = stringResource(id = R.string.password_visibility),
                tint = appPalette.labelSecondary,
            )
        }
    }
}

@Composable
private fun authTextFieldColors(appPalette: AppPalette): TextFieldColors =
    OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = appPalette.supportSeparator,
        focusedBorderColor = appPalette.backContrast,
        unfocusedTextColor = appPalette.labelPrimary,
        focusedTextColor = appPalette.labelPrimary,
    )
