package com.voxeldev.todoapp.designsystem.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.voxeldev.todoapp.designsystem.preview.components.ColorsPreview
import com.voxeldev.todoapp.designsystem.preview.components.PreviewSectionSpacer

/**
 * @author nvoxel
 */
@Composable
internal fun ThemePreview() {
    Column {
        ColorsPreview(colors = primaryColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = secondaryColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = tertiaryColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = errorColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = backgroundColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = surfaceColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = surfaceVariantColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = outlineColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = scrimColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = inverseColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = surfaceShadeColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = surfaceContainerColors())
    }
}

@Composable
private fun primaryColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.primary to "Primary")
    add(MaterialTheme.colorScheme.onPrimary to "On Primary")
    add(MaterialTheme.colorScheme.primaryContainer to "Primary Container")
    add(MaterialTheme.colorScheme.onPrimaryContainer to "On Primary Container")
}

@Composable
private fun secondaryColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.secondary to "Secondary")
    add(MaterialTheme.colorScheme.onSecondary to "On Secondary")
    add(MaterialTheme.colorScheme.secondaryContainer to "Secondary Container")
    add(MaterialTheme.colorScheme.onSecondaryContainer to "On Secondary Container")
}

@Composable
private fun tertiaryColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.tertiary to "Tertiary")
    add(MaterialTheme.colorScheme.onTertiary to "On Tertiary")
    add(MaterialTheme.colorScheme.tertiaryContainer to "Tertiary Container")
    add(MaterialTheme.colorScheme.onTertiaryContainer to "On Tertiary Container")
}

@Composable
private fun errorColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.error to "Error")
    add(MaterialTheme.colorScheme.onError to "On Error")
    add(MaterialTheme.colorScheme.errorContainer to "Error Container")
    add(MaterialTheme.colorScheme.onErrorContainer to "On Error Container")
}

@Composable
private fun backgroundColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.background to "Background")
    add(MaterialTheme.colorScheme.onBackground to "On Background")
}

@Composable
private fun surfaceColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.surface to "Surface")
    add(MaterialTheme.colorScheme.onSurface to "On Surface")
}

@Composable
private fun surfaceVariantColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.surfaceVariant to "Surface Variant")
    add(MaterialTheme.colorScheme.onSurfaceVariant to "On Surface Variant")
}

@Composable
private fun outlineColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.outline to "Outline")
    add(MaterialTheme.colorScheme.outlineVariant to "Outline Variant")
}

@Composable
private fun scrimColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.scrim to "Scrim")
}

@Composable
private fun inverseColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.inverseSurface to "Inverse Surface")
    add(MaterialTheme.colorScheme.inverseOnSurface to "Inverse On Surface")
    add(MaterialTheme.colorScheme.inversePrimary to "Inverse Primary")
}

@Composable
private fun surfaceShadeColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.surfaceDim to "Surface Dim")
    add(MaterialTheme.colorScheme.surfaceBright to "Surface Bright")
}

@Composable
private fun surfaceContainerColors(): List<Pair<Color, String>> = buildList {
    add(MaterialTheme.colorScheme.surfaceContainerLowest to "Surface Container Lowest")
    add(MaterialTheme.colorScheme.surfaceContainerLow to "Surface Container Low")
    add(MaterialTheme.colorScheme.surfaceContainer to "Surface Container")
    add(MaterialTheme.colorScheme.surfaceContainerHigh to "Surface Container High")
    add(MaterialTheme.colorScheme.surfaceContainerHighest to "Surface Container Highest")
}
