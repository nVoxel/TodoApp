package com.voxeldev.todoapp.settings.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun SettingsItem(
    titleText: String,
    descriptionText: String,
    onClick: () -> Unit,
    iconVector: ImageVector,
    isDangerous: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SettingsItemContent(
            titleText = titleText,
            descriptionText = descriptionText,
            iconVector = iconVector,
            isDangerous = isDangerous,
        )
    }
}

@Composable
private fun SettingsItemContent(
    titleText: String,
    descriptionText: String,
    iconVector: ImageVector,
    isDangerous: Boolean,
) {
    val appPalette = LocalAppPalette.current

    Icon(
        imageVector = iconVector,
        contentDescription = titleText,
        tint = if (isDangerous) appPalette.colorRed else appPalette.labelPrimary,
    )

    Spacer(modifier = Modifier.width(width = 16.dp))

    SettingsItemTextContent(
        titleText = titleText,
        descriptionText = descriptionText,
        isDangerous = isDangerous,
    )
}

@Composable
private fun SettingsItemTextContent(
    titleText: String,
    descriptionText: String,
    isDangerous: Boolean,
) {
    val appPalette = LocalAppPalette.current

    Column {
        Text(
            text = titleText,
            color = if (isDangerous) appPalette.colorRed else appPalette.labelPrimary,
            style = AppTypography.body,
        )

        Spacer(modifier = Modifier.height(height = 4.dp))

        Text(
            text = descriptionText,
            color = if (isDangerous) appPalette.colorRedSecondary else appPalette.labelSecondary,
            style = AppTypography.body,
        )
    }
}
