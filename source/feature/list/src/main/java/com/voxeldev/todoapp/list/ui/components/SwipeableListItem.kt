package com.voxeldev.todoapp.list.ui.components

import android.provider.Settings
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.components.swipedismiss.DismissDirection
import com.voxeldev.todoapp.designsystem.components.swipedismiss.DismissValue
import com.voxeldev.todoapp.designsystem.components.swipedismiss.TodoSwipeDismiss
import com.voxeldev.todoapp.designsystem.components.swipedismiss.rememberDismissState
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.list.R
import kotlinx.coroutines.delay
import kotlin.math.roundToLong

private const val DEFAULT_ITEM_ANIMATION_DELAY = 300L

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun SwipeableListItem(
    lazyItemScope: LazyItemScope,
    onDeleteClicked: () -> Unit,
    onCheckClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val appPalette = LocalAppPalette.current

    val contentResolver = LocalContext.current.contentResolver
    val animationDelay = remember {
        val scale = Settings.Global.getFloat(contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f)
        return@remember (DEFAULT_ITEM_ANIMATION_DELAY * scale).roundToLong()
    }

    var isDeleted by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    val swipeToDismissBoxState = rememberDismissState(
        confirmValueChange = { swipeToDismissBoxValue ->
            when (swipeToDismissBoxValue) {
                DismissValue.DismissedToEnd -> isChecked = true
                DismissValue.DismissedToStart -> isDeleted = true
                DismissValue.Default -> return@rememberDismissState false
            }

            return@rememberDismissState true
        },
        positionalThreshold = { it * .5f },
    )

    LaunchedEffect(key1 = isDeleted) {
        if (isDeleted) {
            delay(timeMillis = animationDelay)
            onDeleteClicked()
        }
    }

    LaunchedEffect(key1 = isChecked) {
        if (isChecked) {
            delay(timeMillis = animationDelay)
            onCheckClicked()
            swipeToDismissBoxState.reset()
            isChecked = false
        }
    }

    with(lazyItemScope) {
        Box(modifier = Modifier.animateItemPlacement()) {
            TodoSwipeDismiss(
                state = swipeToDismissBoxState,
                background = {
                    val direction = swipeToDismissBoxState.dismissDirection ?: return@TodoSwipeDismiss

                    val color = if (direction == DismissDirection.StartToEnd) appPalette.colorGreen else appPalette.colorRed
                    val alignment = if (direction == DismissDirection.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
                    val icon = if (direction == DismissDirection.StartToEnd) Icons.Default.Check else Icons.Default.Delete

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = color)
                            .padding(horizontal = 24.dp),
                        contentAlignment = alignment,
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(id = R.string.task_actions),
                            tint = appPalette.colorWhite,
                        )
                    }
                },
                dismissContent = content,
            )
        }
    }
}
