package com.voxeldev.todoapp.designsystem.components

/**
 * @author nvoxel
 */
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.R
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun Error(
    message: String,
    shouldShowRetry: Boolean,
    retryCallback: () -> Unit = {},
) {
    val appPalette = LocalAppPalette.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = appPalette.labelPrimary,
            text = stringResource(id = R.string.error, message),
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
        Error(
            message = "Error while loading something...",
            shouldShowRetry = true,
        )
    }
}
