package com.voxeldev.todoapp.designsystem.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.voxeldev.todoapp.designsystem.preview.AppPalettePreview
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase

/**
 * @author nvoxel
 */
@Immutable
data class AppPalette(
    val supportSeparator: Color = Color.Unspecified,
    val supportOverlay: Color = Color.Unspecified,

    val labelPrimary: Color = Color.Unspecified,
    val labelSecondary: Color = Color.Unspecified,
    val labelTertiary: Color = Color.Unspecified,
    val labelDisable: Color = Color.Unspecified,

    val colorRed: Color = Color.Unspecified,
    val colorRedBack: Color = Color.Unspecified,
    val colorGreen: Color = Color.Unspecified,
    val colorBlue: Color = Color.Unspecified,
    val colorBlueBack: Color = Color.Unspecified,
    val colorBlueSelection: Color = Color.Unspecified,
    val colorGray: Color = Color.Unspecified,
    val colorGrayLight: Color = Color.Unspecified,
    val colorWhite: Color = Color.Unspecified,

    val backPrimary: Color = Color.Unspecified,
    val backSecondary: Color = Color.Unspecified,
    val backElevated: Color = Color.Unspecified,
)

val LocalAppPalette = staticCompositionLocalOf { AppPalette() }

internal val lightAppPalette = AppPalette(
    supportSeparator = Color(0x33000000),
    supportOverlay = Color(0x0F000000),

    labelPrimary = Color(0xFF000000),
    labelSecondary = Color(0x99000000),
    labelTertiary = Color(0x4D000000),
    labelDisable = Color(0x26000000),

    colorRed = Color(0xFFFF3B30),
    colorRedBack = Color(0x14FF3B30),
    colorGreen = Color(0xFF34C759),
    colorBlue = Color(0xFF007AFF),
    colorBlueBack = Color(0x28007AFF),
    colorBlueSelection = Color(0x50007AFF),
    colorGray = Color(0xFF8E8E93),
    colorGrayLight = Color(0xFFD1D1D6),
    colorWhite = Color(0xFFFFFFFF),

    backPrimary = Color(0xFFF7F6F2),
    backSecondary = Color(0xFFFFFFFF),
    backElevated = Color(0xFFFFFFFF),
)

internal val darkAppPalette = AppPalette(
    supportSeparator = Color(0x33FFFFFF),
    supportOverlay = Color(0x52000000),

    labelPrimary = Color(0xFFFFFFFF),
    labelSecondary = Color(0x99FFFFFF),
    labelTertiary = Color(0x66FFFFFF),
    labelDisable = Color(0x26FFFFFF),

    colorRed = Color(0xFFFF453A),
    colorRedBack = Color(0x14FF453A),
    colorGreen = Color(0xFF32D74B),
    colorBlue = Color(0xFF0A84FF),
    colorBlueBack = Color(0x280A84FF),
    colorBlueSelection = Color(0x500A84FF),
    colorGray = Color(0xFF8E8E93),
    colorGrayLight = Color(0xFF48484A),
    colorWhite = Color(0xFFFFFFFF),

    backPrimary = Color(0xFF161618),
    backSecondary = Color(0xFF252528),
    backElevated = Color(0xFF3C3C3F),
)

@Preview(name = "Component colors (Light)", widthDp = 1440, heightDp = 460, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Component colors (Dark)", widthDp = 1440, heightDp = 460, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    PreviewBase {
        AppPalettePreview()
    }
}
