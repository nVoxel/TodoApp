package com.voxeldev.todoapp.auth.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun PasswordMethodCard(
    loginText: String,
    passwordText: String,
    onUpdateLoginText: (String) -> Unit,
    onUpdatePasswordText: (String) -> Unit,
    showLoading: Boolean,
    error: Throwable?,
    onRetryClicked: () -> Unit,
    onCheckAuthClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    AuthCard(
        showClose = true,
        onCloseClicked = onCloseClicked,
        showLoading = showLoading,
        error = error,
        retryCallback = onRetryClicked,
    ) { isForegroundVisible ->
        Text(
            text = stringResource(id = R.string.password_method_using),
            color = appPalette.labelSecondary,
            style = AppTypography.body,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            text = loginText,
            onTextChanged = onUpdateLoginText,
            placeholderText = stringResource(id = R.string.login),
            enabled = isForegroundVisible,
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            text = passwordText,
            onTextChanged = onUpdatePasswordText,
            placeholderText = stringResource(id = R.string.password),
            enabled = isForegroundVisible,
            secure = true,
            onDoneClicked = onCheckAuthClicked,
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AuthContrastButton(
            onClick = onCheckAuthClicked,
            enabled = isForegroundVisible,
        ) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.sign_in),
                fontSize = 18.sp,
                style = AppTypography.button,
            )
        }
    }
}
