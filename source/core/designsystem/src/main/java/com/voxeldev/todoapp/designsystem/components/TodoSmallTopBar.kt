package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.R
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoSmallTopBar(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    displayTitle: Boolean = false,
    titleText: String = "",
    displayButton: Boolean = false,
    buttonText: String = "",
    isButtonActive: Boolean = true,
    onButtonClicked: () -> Unit = {},
) {
    val appPalette = LocalAppPalette.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 72.dp)
            .background(color = appPalette.backPrimary)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TodoSmallTopBarContent(
            onCloseClicked = onCloseClicked,
            displayTitle = displayTitle,
            titleText = titleText,
        )

        TodoSmallTopBarButton(
            displayButton = displayButton,
            buttonText = buttonText,
            isButtonActive = isButtonActive,
            onButtonClicked = onButtonClicked,
        )
    }
}

@Composable
private fun RowScope.TodoSmallTopBarContent(
    onCloseClicked: () -> Unit,
    displayTitle: Boolean,
    titleText: String,
) {
    val appPalette = LocalAppPalette.current

    Icon(
        modifier = Modifier
            .size(size = 24.dp)
            .clip(shape = CircleShape)
            .clickable(onClick = onCloseClicked),
        imageVector = Icons.Default.Close,
        tint = appPalette.labelPrimary,
        contentDescription = stringResource(id = R.string.close),
    )

    Spacer(modifier = Modifier.width(width = 16.dp))

    Row(modifier = Modifier.weight(weight = 1f)) {
        if (displayTitle) {
            Text(
                text = titleText,
                color = appPalette.labelPrimary,
                style = AppTypography.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun TodoSmallTopBarButton(
    displayButton: Boolean,
    buttonText: String,
    isButtonActive: Boolean,
    onButtonClicked: () -> Unit,
) {
    Spacer(modifier = Modifier.width(width = 16.dp))

    if (displayButton) {
        TodoTextButton(
            text = buttonText,
            onClick = onButtonClicked,
            enabled = isButtonActive,
        )
    }
}

@ComponentDayNightPreviews
@Composable
private fun Preview() {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoSmallTopBar(
                displayTitle = true,
                titleText = "Title Text",
                displayButton = true,
                buttonText = "text button",
                isButtonActive = true,
                onButtonClicked = {},
                onCloseClicked = {},
            )
        }
    }
}
