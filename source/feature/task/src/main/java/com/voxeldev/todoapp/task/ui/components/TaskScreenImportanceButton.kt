package com.voxeldev.todoapp.task.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.task.R
import com.voxeldev.todoapp.task.ui.extensions.getDisplayColor
import com.voxeldev.todoapp.task.ui.extensions.getDisplayText
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

private const val INITIAL_RADIUS = 0f
private const val TARGET_RADIUS_MULTIPLIER = 1.35f
private const val INITIAL_ALPHA = 0.75f
private const val TARGET_ALPHA = 0f
private const val EFFECT_DURATION_MILLIS = 1000

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskScreenImportanceButton(
    importance: TodoItemImportance,
    importanceSheetVisible: Boolean,
    onImportanceChanged: (TodoItemImportance) -> Unit,
    onChangeImportanceSheetVisibility: (Boolean) -> Unit,
) {
    val appPalette = LocalAppPalette.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    var animated by remember { mutableStateOf(false) }
    val transitionRadius = remember { Animatable(initialValue = INITIAL_RADIUS) }
    val transitionAlpha = remember { Animatable(initialValue = INITIAL_ALPHA) }

    LaunchedEffect(key1 = animated) {
        if (!animated) return@LaunchedEffect

        listOf(
            launch {
                transitionRadius.animateTo(
                    targetValue = screenWidth * TARGET_RADIUS_MULTIPLIER,
                    animationSpec = tween(durationMillis = EFFECT_DURATION_MILLIS),
                )
            },
            launch {
                transitionAlpha.animateTo(
                    targetValue = TARGET_ALPHA,
                    animationSpec = tween(durationMillis = EFFECT_DURATION_MILLIS),
                )
            },
        ).joinAll()

        transitionRadius.snapTo(INITIAL_RADIUS)
        transitionAlpha.snapTo(INITIAL_ALPHA)
        animated = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChangeImportanceSheetVisibility(true) }
            .drawBehind {
                clipRect {
                    drawCircle(
                        color = appPalette.colorRed,
                        radius = transitionRadius.value,
                        center = center,
                        alpha = transitionAlpha.value,
                    )
                }
            }
            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.importance),
            color = appPalette.labelPrimary,
            style = AppTypography.body,
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = importance.getDisplayText(),
            color = importance.getDisplayColor(forDropdown = false),
            style = AppTypography.subhead,
        )

        ImportanceBottomSheet(
            isVisible = importanceSheetVisible,
            sheetState = sheetState,
            onDismiss = { onChangeImportanceSheetVisibility(false) },
            onImportanceClicked = { updatedImportance ->
                onImportanceChanged(updatedImportance)
            if (updatedImportance != importance && updatedImportance == TodoItemImportance.Urgent) {
                    animated = true
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImportanceBottomSheet(
    isVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onImportanceClicked: (TodoItemImportance) -> Unit,
) {
    val appPalette = LocalAppPalette.current
    val coroutineScope = rememberCoroutineScope()

    if (!isVisible) return

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = appPalette.backElevated,
        onDismissRequest = onDismiss,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
            text = stringResource(id = R.string.importance),
            color = appPalette.labelPrimary,
            style = AppTypography.largeTitle,
        )

        TodoItemImportance.entries.forEach { importance ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onImportanceClicked(importance)
                        coroutineScope
                            .launch {
                                sheetState.hide()
                            }
                            .invokeOnCompletion {
                                onDismiss()
                            }
                    }
                    .padding(horizontal = 32.dp, vertical = 16.dp),
            ) {
                Text(
                    text = importance.getDisplayText(),
                    color = importance.getDisplayColor(forDropdown = true),
                    style = AppTypography.body,
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
    }
}
