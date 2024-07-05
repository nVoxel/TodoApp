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
import com.voxeldev.todoapp.designsystem.components.swipedismiss.DismissState
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SwipeableListItem(
    lazyItemScope: LazyItemScope,
    onDeleteClicked: () -> Unit,
    onCheckClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    var isDeleted by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        onChangeChecked = { newIsChecked -> isChecked = newIsChecked },
        onChangeDeleted = { newIsDeleted -> isDeleted = newIsDeleted },
    )

    SideEffects(
        swipeToDismissBoxState = swipeToDismissBoxState,
        isDeleted = isDeleted,
        isChecked = isChecked,
        changeIsChecked = { newIsChecked -> isChecked = newIsChecked },
        changeIsDeleted = { newIsDeleted -> isDeleted = newIsDeleted },
        onDeleteClicked = onDeleteClicked,
        onCheckClicked = onCheckClicked,
    )

    SwipeableListItemContent(
        lazyItemScope = lazyItemScope,
        swipeToDismissBoxState = swipeToDismissBoxState,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberSwipeToDismissBoxState(
    onChangeChecked: (Boolean) -> Unit,
    onChangeDeleted: (Boolean) -> Unit,
): DismissState =
    rememberDismissState(
        confirmValueChange = { swipeToDismissBoxValue ->
            when (swipeToDismissBoxValue) {
                DismissValue.DismissedToEnd -> onChangeChecked(true)
                DismissValue.DismissedToStart -> onChangeDeleted(true)
                DismissValue.Default -> return@rememberDismissState false
            }

            return@rememberDismissState true
        },
        positionalThreshold = { it * .5f },
    )

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableListItemContent(
    lazyItemScope: LazyItemScope,
    swipeToDismissBoxState: DismissState,
    content: @Composable RowScope.() -> Unit,
) {
    with(lazyItemScope) {
        Box(modifier = Modifier.animateItemPlacement()) {
            TodoSwipeDismiss(
                state = swipeToDismissBoxState,
                background = { SwipeableListItemBackground(swipeToDismissBoxState = swipeToDismissBoxState) },
                dismissContent = content,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableListItemBackground(swipeToDismissBoxState: DismissState) {
    val appPalette = LocalAppPalette.current

    val direction = swipeToDismissBoxState.dismissDirection ?: return

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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SideEffects(
    swipeToDismissBoxState: DismissState,
    isDeleted: Boolean,
    isChecked: Boolean,
    changeIsDeleted: (Boolean) -> Unit,
    changeIsChecked: (Boolean) -> Unit,
    onDeleteClicked: () -> Unit,
    onCheckClicked: () -> Unit,
) {
    val contentResolver = LocalContext.current.contentResolver
    val animationDelay = remember {
        val scale = Settings.Global.getFloat(contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f)
        return@remember (DEFAULT_ITEM_ANIMATION_DELAY * scale).roundToLong()
    }

    LaunchedEffect(key1 = isDeleted) {
        if (isDeleted) {
            delay(timeMillis = animationDelay)
            onDeleteClicked()
            swipeToDismissBoxState.reset()
            changeIsDeleted(false)
        }
    }

    LaunchedEffect(key1 = isChecked) {
        if (isChecked) {
            delay(timeMillis = animationDelay)
            onCheckClicked()
            swipeToDismissBoxState.reset()
            changeIsChecked(false)
        }
    }
}
