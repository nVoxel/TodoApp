package com.voxeldev.todoapp.designsystem.components

/**
 * @author nvoxel
 */
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.R
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun FullscreenError(
    message: String,
    shouldShowRetry: Boolean,
    retryCallback: () -> Unit = {},
) {
    val appPalette = LocalAppPalette.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = appPalette.backPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Error(
            message = message,
            shouldShowRetry = shouldShowRetry,
            retryCallback = retryCallback,
        )
    }
}

@Composable
fun Error(
    message: String,
    shouldShowRetry: Boolean,
    retryCallback: () -> Unit = {},
) {
    val appPalette = LocalAppPalette.current

    Column(
        modifier = Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = appPalette.labelPrimary,
            text = stringResource(id = R.string.error, message),
            textAlign = TextAlign.Center,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = AppTypography.body,
        )
        if (shouldShowRetry) {
            TodoButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { retryCallback() },
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.RestartAlt,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 2.dp),
                    text = stringResource(id = R.string.retry),
                )
            }
        }
    }
}

@ComponentDayNightPreviews
@Composable
private fun Preview() {
    PreviewBase {
        FullscreenError(
            message = "Error while loading something...",
            shouldShowRetry = true,
        )
    }
}
