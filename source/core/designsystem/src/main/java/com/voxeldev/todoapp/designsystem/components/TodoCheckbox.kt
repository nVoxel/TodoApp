package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isImportant: Boolean,
) {
    val appPalette = LocalAppPalette.current

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Checkbox(
            modifier = modifier
                .size(size = 20.dp),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxColors(
                checkedCheckmarkColor = appPalette.colorWhite,
                uncheckedCheckmarkColor = Color.Transparent,
                checkedBoxColor = appPalette.colorGreen,
                uncheckedBoxColor = if (isImportant) appPalette.colorRedBack else Color.Transparent,
                disabledCheckedBoxColor = Color.Transparent,
                disabledUncheckedBoxColor = Color.Transparent,
                disabledIndeterminateBorderColor = Color.Transparent,
                checkedBorderColor = appPalette.colorGreen,
                uncheckedBorderColor = if (isImportant) appPalette.colorRed else appPalette.supportSeparator,
                disabledBorderColor = Color.Transparent,
                disabledUncheckedBorderColor = Color.Transparent,
                disabledIndeterminateBoxColor = Color.Transparent,
            ),
        )
    }
}
