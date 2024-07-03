package com.voxeldev.todoapp.designsystem.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.components.PreviewSectionSpacer
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun AppTypographyPreview() {
    val appPalette = LocalAppPalette.current

    Surface(
        modifier = Modifier.wrapContentSize(),
        color = appPalette.backSecondary,
    ) {
        Column(modifier = Modifier.padding(all = 32.dp)) {
            Text(
                text = "Large title — 32/38",
                style = AppTypography.largeTitle,
            )

            PreviewSectionSpacer()

            Text(
                text = "Title — 20/32",
                style = AppTypography.title,
            )

            PreviewSectionSpacer()

            Text(
                text = "BUTTON — 14/24",
                style = AppTypography.button,
            )

            PreviewSectionSpacer()

            Text(
                text = "Body — 16/20",
                style = AppTypography.body,
            )

            PreviewSectionSpacer()

            Text(
                text = "Body strikethrough — 16/20",
                style = AppTypography.bodyStrikethrough,
            )

            PreviewSectionSpacer()

            Text(
                text = "Subhead — 14/20",
                style = AppTypography.subhead,
            )

            PreviewSectionSpacer()

            Text(
                text = "Primary Text Field — 24/32",
                style = AppTypography.primaryTextField,
            )
        }
    }
}
