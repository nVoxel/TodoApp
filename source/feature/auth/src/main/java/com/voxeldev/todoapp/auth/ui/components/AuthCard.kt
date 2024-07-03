package com.voxeldev.todoapp.auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.designsystem.components.Error
import com.voxeldev.todoapp.designsystem.components.Loader
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.utils.extensions.getDisplayMessage

/**
 * @author nvoxel
 */
@Composable
internal fun AuthCard(
    modifier: Modifier = Modifier,
    showClose: Boolean = false,
    onCloseClicked: () -> Unit = {},
    showLoading: Boolean = false,
    error: Throwable? = null,
    retryCallback: () -> Unit = {},
    cardContent: @Composable ColumnScope.(isForegroundVisible: Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    val appPalette = LocalAppPalette.current
    val isForegroundVisible = !showLoading && error == null

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 32.dp))
            .background(color = appPalette.backPrimary)
            .verticalScroll(state = scrollState),
        contentAlignment = Alignment.Center,
    ) {
        if (showLoading) {
            Loader()
        } else if (error != null) {
            Box(modifier = Modifier.zIndex(zIndex = 1f)) {
                Error(
                    message = error.getDisplayMessage(),
                    shouldShowRetry = true,
                    retryCallback = retryCallback,
                )
            }
        }

        Box(modifier = Modifier.alpha(if (isForegroundVisible) 1f else 0f)) {
            if (showClose) {
                Icon(
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .padding(all = 12.dp)
                        .clip(shape = CircleShape)
                        .clickable(enabled = isForegroundVisible, onClick = onCloseClicked)
                        .padding(all = 12.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = appPalette.labelPrimary,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(height = 8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .background(color = appPalette.colorBlue)
                            .padding(all = 8.dp),
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = stringResource(id = R.string.auth_header),
                        tint = appPalette.colorWhite,
                    )

                    Spacer(modifier = Modifier.width(width = 16.dp))

                    Text(
                        text = stringResource(id = R.string.auth_header),
                        color = appPalette.labelPrimary,
                        style = AppTypography.title,
                    )
                }

                Spacer(modifier = Modifier.height(height = 24.dp))

                cardContent(isForegroundVisible)
            }
        }
    }
}
