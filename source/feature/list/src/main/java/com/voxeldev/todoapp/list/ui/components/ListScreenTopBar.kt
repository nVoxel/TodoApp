package com.voxeldev.todoapp.list.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.components.TodoLargeTopBar
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.list.R

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListScreenTopBar(
    topBarScrollBehavior: TopAppBarScrollBehavior,
    completedItemsCount: Int,
    isOnlyUncompletedVisible: Boolean,
    onUncompletedVisibilityChanged: (Boolean) -> Unit,
    onSettingsClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    TodoLargeTopBar(
        titlePrimary = {
            Text(text = stringResource(id = R.string.my_todos))
        },
        titleSecondary = {
            Text(
                text = stringResource(id = R.string.completed, completedItemsCount),
                style = AppTypography.body,
                color = appPalette.labelTertiary,
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable { onUncompletedVisibilityChanged(!isOnlyUncompletedVisible) },
                imageVector = if (isOnlyUncompletedVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = stringResource(id = R.string.toggle_complete),
                tint = appPalette.colorBlue,
            )

            Spacer(modifier = Modifier.width(width = 16.dp))

            Icon(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable(onClick = onSettingsClicked),
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings),
                tint = appPalette.colorBlue,
            )
        },
        scrollBehavior = topBarScrollBehavior,
    )
}
