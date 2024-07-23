package com.voxeldev.todoapp.auth.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.designsystem.components.TodoButton
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.icons.AdditionalIcons
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun ChooseMethodCard(
    onCredentialsMethodClicked: () -> Unit,
    onOAuthMethodClicked: () -> Unit,
    showLoading: Boolean,
    error: Exception?,
    onRetryClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    AuthCard(
        showLoading = showLoading,
        error = error,
        retryCallback = onRetryClicked,
    ) {
        Text(
            text = stringResource(id = R.string.choose_method),
            color = appPalette.labelSecondary,
            style = AppTypography.body,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        TodoButton(
            modifier = Modifier
                .defaultMinSize(minHeight = 58.dp)
                .fillMaxWidth(),
            onClick = onCredentialsMethodClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = appPalette.colorGrayLight.copy(alpha = 0.5f),
                contentColor = appPalette.labelPrimary,
            ),
            shape = RoundedCornerShape(size = 24.dp),
        ) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.password_method),
                fontSize = 18.sp,
                style = AppTypography.button,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(width = 24.dp))

            TodoDivider(modifier = Modifier.weight(weight = 0.5f))

            Spacer(modifier = Modifier.width(width = 24.dp))

            Text(
                text = stringResource(id = R.string.methods_divider),
                color = appPalette.labelTertiary,
            )

            Spacer(modifier = Modifier.width(width = 24.dp))

            TodoDivider(modifier = Modifier.weight(weight = 0.5f))

            Spacer(modifier = Modifier.width(width = 24.dp))
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        AuthContrastButton(onClick = onOAuthMethodClicked) {
            Image(
                modifier = Modifier
                    .size(size = 24.dp)
                    .clip(shape = CircleShape),
                imageVector = AdditionalIcons.YandexLogo,
                contentDescription = stringResource(id = R.string.yandex_method),
            )

            Spacer(modifier = Modifier.width(width = 4.dp))

            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.yandex_method),
                fontSize = 18.sp,
                style = AppTypography.button,
                textAlign = TextAlign.Center,
            )
        }
    }
}
