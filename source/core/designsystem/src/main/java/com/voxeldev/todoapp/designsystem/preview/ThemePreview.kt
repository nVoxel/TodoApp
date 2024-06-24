package com.voxeldev.todoapp.designsystem.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.voxeldev.todoapp.designsystem.preview.components.PreviewColorCard
import com.voxeldev.todoapp.designsystem.preview.components.PreviewSectionSpacer

/**
 * @author nvoxel
 */
@Composable
internal fun ThemePreview() {
    Column {
        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.primary,
                colorNameLabel = "Primary",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onPrimary,
                colorNameLabel = "On Primary",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.primaryContainer,
                colorNameLabel = "Primary Container",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                colorNameLabel = "On Primary Container",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.secondary,
                colorNameLabel = "Secondary",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onSecondary,
                colorNameLabel = "On Secondary",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.secondaryContainer,
                colorNameLabel = "Secondary Container",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                colorNameLabel = "On Secondary Container",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.tertiary,
                colorNameLabel = "Tertiary",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onTertiary,
                colorNameLabel = "On Tertiary",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                colorNameLabel = "Tertiary Container",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                colorNameLabel = "On Tertiary Container",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.error,
                colorNameLabel = "Error",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onError,
                colorNameLabel = "On Error",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.errorContainer,
                colorNameLabel = "Error Container",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onErrorContainer,
                colorNameLabel = "On Error Container",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.background,
                colorNameLabel = "Background",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onBackground,
                colorNameLabel = "On Background",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.surface,
                colorNameLabel = "Surface",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onSurface,
                colorNameLabel = "On Surface",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceVariant,
                colorNameLabel = "Surface Variant",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                colorNameLabel = "On Surface Variant",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.outline,
                colorNameLabel = "Outline",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.outlineVariant,
                colorNameLabel = "Outline Variant",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.scrim,
                colorNameLabel = "Scrim",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.inverseSurface,
                colorNameLabel = "Inverse Surface",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                colorNameLabel = "Inverse On Surface",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.inversePrimary,
                colorNameLabel = "Inverse Primary",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceDim,
                colorNameLabel = "Surface Dim",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceBright,
                colorNameLabel = "Surface Bright",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                colorNameLabel = "Surface Container Lowest",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                colorNameLabel = "Surface Container Low",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceContainer,
                colorNameLabel = "Surface Container",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                colorNameLabel = "Surface Container High",
            )

            PreviewColorCard(
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                colorNameLabel = "Surface Container Highest",
            )
        }
    }
}
