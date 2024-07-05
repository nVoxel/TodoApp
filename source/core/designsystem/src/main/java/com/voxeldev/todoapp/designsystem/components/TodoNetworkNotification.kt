package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.R
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoNetworkNotification(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClick: () -> Unit,
) {
    BottomAnimatedVisibility(isVisible = isVisible) {
        NetworkNotification(
            modifier = modifier,
            onClick = onClick,
        )
    }
}

@Composable
private fun NetworkNotification(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    ExtendedFloatingActionButton(
        modifier = modifier
            .padding(all = 18.dp)
            .navigationBarsPadding(),
        text = {
            Text(
                text = stringResource(id = R.string.network_lost),
                color = appPalette.labelPrimary,
                style = AppTypography.button,
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Default.WifiOff,
                contentDescription = stringResource(id = R.string.network_lost),
                tint = appPalette.colorRed,
            )
        },
        onClick = onClick,
    )
}
