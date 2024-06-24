package com.voxeldev.todoapp.designsystem.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.voxeldev.todoapp.designsystem.preview.components.PreviewColorCard
import com.voxeldev.todoapp.designsystem.preview.components.PreviewSectionSpacer
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun AppPalettePreview() {
    val appPalette = LocalAppPalette.current

    Column {
        Row {
            PreviewColorCard(
                color = appPalette.supportSeparator,
                colorNameLabel = "Support / Separator",
            )

            PreviewColorCard(
                color = appPalette.supportOverlay,
                colorNameLabel = "Support / Overlay",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = appPalette.labelPrimary,
                colorNameLabel = "Label / Primary",
            )

            PreviewColorCard(
                color = appPalette.labelSecondary,
                colorNameLabel = "Label / Secondary",
            )

            PreviewColorCard(
                color = appPalette.labelTertiary,
                colorNameLabel = "Label / Tertiary",
            )

            PreviewColorCard(
                color = appPalette.labelDisable,
                colorNameLabel = "Label / Disable",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = appPalette.colorRed,
                colorNameLabel = "Color / Red",
            )

            PreviewColorCard(
                color = appPalette.colorGreen,
                colorNameLabel = "Color / Green",
            )

            PreviewColorCard(
                color = appPalette.colorBlue,
                colorNameLabel = "Color / Blue",
            )

            PreviewColorCard(
                color = appPalette.colorGray,
                colorNameLabel = "Color / Gray",
            )

            PreviewColorCard(
                color = appPalette.colorGrayLight,
                colorNameLabel = "Color / Gray Light",
            )

            PreviewColorCard(
                color = appPalette.colorWhite,
                colorNameLabel = "Color / White",
            )
        }

        PreviewSectionSpacer()

        Row {
            PreviewColorCard(
                color = appPalette.backPrimary,
                colorNameLabel = "Back / Primary",
            )

            PreviewColorCard(
                color = appPalette.backSecondary,
                colorNameLabel = "Back / Secondary",
            )

            PreviewColorCard(
                color = appPalette.backElevated,
                colorNameLabel = "Back / Elevated",
            )
        }
    }
}
