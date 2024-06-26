package com.voxeldev.todoapp.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.R
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoSmallFAB(
    modifier: Modifier = Modifier,
    isFabVisible: Boolean,
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    AnimatedVisibility(
        visible = isFabVisible,
        enter = slideInVertically(initialOffsetY = { it * 2 }),
        exit = slideOutVertically(targetOffsetY = { it * 2 }),
    ) {
        SmallFloatingActionButton(
            modifier = modifier
                .size(size = 56.dp),
            onClick = onClick,
            shape = CircleShape,
            containerColor = appPalette.colorBlue,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_task),
                tint = appPalette.colorWhite,
            )
        }
    }
}
