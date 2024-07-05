package com.voxeldev.todoapp.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.list.R

/**
 * @author nvoxel
 */
@Composable
internal fun NewListItem(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = appPalette.backSecondary)
            .clickable(onClick = onClicked)
            .padding(horizontal = 48.dp, vertical = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.new_task),
            color = appPalette.labelTertiary,
            style = AppTypography.body,
        )
    }
}
