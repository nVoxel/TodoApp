package com.voxeldev.todoapp.designsystem.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author nvoxel
 */
@Composable
internal fun PreviewColorCard(
    color: Color,
    colorNameLabel: String,
) {
    Column(
        modifier = Modifier
            .background(color = color)
            .size(width = 240.dp, height = 100.dp)
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Text(
            text = modifyColorNameLabel(colorNameLabel = colorNameLabel),
            color = determineTextColor(backgroundColor = color),
            fontSize = 12.sp,
        )

        Text(
            text = String.format("#%08X", color.toArgb()),
            color = determineTextColor(backgroundColor = color),
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun modifyColorNameLabel(colorNameLabel: String): String {
    val themeValue = if (isSystemInDarkTheme()) "Dark" else "Light"

    var slashIndex = colorNameLabel.indexOf('/') + 1
    if (slashIndex == 0) slashIndex = colorNameLabel.length

    val builder = StringBuilder(colorNameLabel)
    builder.insert(slashIndex, " [$themeValue]")
    return builder.toString()
}

private fun determineTextColor(backgroundColor: Color): Color {
    return if (backgroundColor.luminance() > 0.5) Color.Black else Color.White
}
