package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.R
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoSmallTopBar(
    modifier: Modifier = Modifier,
    buttonText: String,
    onButtonClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = appPalette.backPrimary)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .size(size = 24.dp)
                .clip(shape = CircleShape)
                .clickable(onClick = onCloseClicked),
            imageVector = Icons.Default.Close,
            tint = appPalette.labelPrimary,
            contentDescription = stringResource(id = R.string.close),
        )

        TodoTextButton(
            text = buttonText,
            onClick = onButtonClicked,
        )
    }
}
