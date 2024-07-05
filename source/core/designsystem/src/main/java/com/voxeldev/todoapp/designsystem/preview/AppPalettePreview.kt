package com.voxeldev.todoapp.designsystem.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.voxeldev.todoapp.designsystem.preview.components.ColorsPreview
import com.voxeldev.todoapp.designsystem.preview.components.PreviewSectionSpacer
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun AppPalettePreview() {
    Column {
        ColorsPreview(colors = supportColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = labelColors())
        PreviewSectionSpacer()
        ColorsPreview(colors = colors())
        PreviewSectionSpacer()
        ColorsPreview(colors = backColors())
    }
}

@Composable
private fun supportColors(): List<Pair<Color, String>> {
    val appPalette = LocalAppPalette.current

    return buildList {
        add(appPalette.supportSeparator to "Support / Separator")
        add(appPalette.supportOverlay to "Support / Overlay")
    }
}

@Composable
private fun labelColors(): List<Pair<Color, String>> {
    val appPalette = LocalAppPalette.current

    return buildList {
        add(appPalette.labelPrimary to "Label / Primary")
        add(appPalette.labelSecondary to "Label / Secondary")
        add(appPalette.labelTertiary to "Label / Tertiary")
        add(appPalette.labelDisable to "Label / Disable")
        add(appPalette.labelContrast to "Label / Contrast")
        add(appPalette.labelSupport to "Label / Support")
    }
}

@Composable
private fun colors(): List<Pair<Color, String>> {
    val appPalette = LocalAppPalette.current

    return buildList {
        add(appPalette.colorRed to "Color / Red")
        add(appPalette.colorRedSecondary to "Color / Red Secondary")
        add(appPalette.colorRedBack to "Color / Red Back")
        add(appPalette.colorGreen to "Color / Green")
        add(appPalette.colorBlue to "Color / Blue")
        add(appPalette.colorBlueBack to "Color / Blue Back")
        add(appPalette.colorBlueSelection to "Color / Blue Selection")
        add(appPalette.colorGray to "Color / Gray")
        add(appPalette.colorGrayLight to "Color / Gray Light")
        add(appPalette.colorWhite to "Color / White")
    }
}

@Composable
private fun backColors(): List<Pair<Color, String>> {
    val appPalette = LocalAppPalette.current

    return buildList {
        add(appPalette.backPrimary to "Back / Primary")
        add(appPalette.backSecondary to "Back / Secondary")
        add(appPalette.backElevated to "Back / Elevated")
        add(appPalette.backTint to "Back / Tint")
        add(appPalette.backContrast to "Back / Contrast")
    }
}
