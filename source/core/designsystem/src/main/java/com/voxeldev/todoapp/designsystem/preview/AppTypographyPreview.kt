package com.voxeldev.todoapp.designsystem.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.components.PreviewSectionSpacer
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

private val textStyles: List<Pair<String, TextStyle>> = buildList {
    add("Large title — 32/38" to AppTypography.largeTitle)
    add("Title — 20/32" to AppTypography.title)
    add("BUTTON — 14/24" to AppTypography.button)
    add("Body — 16/20" to AppTypography.body)
    add("Body strikethrough — 16/20" to AppTypography.bodyStrikethrough)
    add("Subhead — 14/20" to AppTypography.subhead)
    add("Primary Text Field — 24/32" to AppTypography.primaryTextField)
}

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
            textStyles.forEachIndexed { index, textStyle ->
                Text(
                    text = textStyle.first,
                    style = textStyle.second,
                )

                if (index < textStyles.lastIndex) {
                    PreviewSectionSpacer()
                }
            }
        }
    }
}
