package com.voxeldev.todoapp.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.components.TodoCheckbox
import com.voxeldev.todoapp.designsystem.components.TodoLargeTopBar
import com.voxeldev.todoapp.designsystem.components.TodoSmallFAB
import com.voxeldev.todoapp.designsystem.icons.AdditionalIcons
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.list.R
import com.voxeldev.todoapp.list.ui.components.SwipeableListItem
import com.voxeldev.todoapp.list.ui.components.TodoItemInfoDialog
import com.voxeldev.todoapp.list.ui.preview.ListScreenPreviewData
import com.voxeldev.todoapp.list.viewmodel.ListViewModel

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onNavigateToTask: (String?) -> Unit,
    viewModel: ListViewModel,
) {
    val lazyColumnState = rememberLazyListState()
    val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state = rememberTopAppBarState())

    var isFabVisible by rememberSaveable { mutableStateOf(true) }
    var isOnlyUncompletedVisible by rememberSaveable { mutableStateOf(false) }

    val todoItems by viewModel.todoItems.collectAsStateWithLifecycle()
    val completedItemsCount by viewModel.completedItemsCount.collectAsStateWithLifecycle()

    BaseScreen(
        viewModel = viewModel,
        retryCallback = viewModel::getTodoItems,
    ) {
        ListScreen(
            lazyColumnState = lazyColumnState,
            topBarScrollBehavior = topBarScrollBehavior,
            isFabVisible = isFabVisible,
            onFabVisibleChanged = { updatedIsFabVisible -> isFabVisible = updatedIsFabVisible },
            isOnlyUncompletedVisible = isOnlyUncompletedVisible,
            onUncompletedVisibilityChanged = { updatedIsOnlyUncompletedVisible ->
                isOnlyUncompletedVisible = updatedIsOnlyUncompletedVisible
            },
            todoItems = todoItems,
            completedItemsCount = completedItemsCount,
            onItemClicked = onNavigateToTask,
            onCheckClicked = { id, isChecked ->
                viewModel.checkTodoItem(
                    id = id,
                    isChecked = isChecked,
                )
            },
            onDeleteClicked = { id ->
                viewModel.deleteTodoItem(id = id)
            },
            onRequestFormattedTimestamp = viewModel::getFormattedTimestamp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    lazyColumnState: LazyListState,
    topBarScrollBehavior: TopAppBarScrollBehavior,
    isFabVisible: Boolean,
    onFabVisibleChanged: (Boolean) -> Unit,
    isOnlyUncompletedVisible: Boolean,
    onUncompletedVisibilityChanged: (Boolean) -> Unit,
    todoItems: List<TodoItem>,
    completedItemsCount: Int,
    onItemClicked: (String?) -> Unit,
    onCheckClicked: (String, Boolean) -> Unit,
    onDeleteClicked: (String) -> Unit,
    onRequestFormattedTimestamp: (Long) -> String,
) {
    val appPalette = LocalAppPalette.current

    val fabNestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < -1) onFabVisibleChanged(false)
                if (available.y > 1) onFabVisibleChanged(true)
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(connection = fabNestedScrollConnection),
        topBar = {
            TodoLargeTopBar(
                titlePrimary = {
                    Text(text = stringResource(id = R.string.my_todos))
                },
                titleSecondary = {
                    Text(
                        text = stringResource(id = R.string.completed, completedItemsCount),
                        style = AppTypography.body,
                        color = appPalette.labelTertiary,
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .clickable { onUncompletedVisibilityChanged(!isOnlyUncompletedVisible) },
                        imageVector = if (isOnlyUncompletedVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = stringResource(id = R.string.toggle_complete),
                        tint = appPalette.colorBlue,
                    )
                },
                scrollBehavior = topBarScrollBehavior,
            )
        },
        floatingActionButton = {
            TodoSmallFAB(
                isFabVisible = isFabVisible,
                onClick = { onItemClicked(null) },
            )
        },
        containerColor = appPalette.backPrimary,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(start = 8.dp, end = 8.dp, top = 2.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(size = 8.dp),
                )
                .background(
                    color = appPalette.backSecondary,
                    shape = RoundedCornerShape(size = 8.dp),
                ),
            state = lazyColumnState,
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 24.dp,
            ),
        ) {
            items(items = todoItems, key = { it.id }) { todoItem ->
                if (isOnlyUncompletedVisible && todoItem.isComplete) return@items

                SwipeableListItem(
                    lazyItemScope = this,
                    onDeleteClicked = { onDeleteClicked(todoItem.id) },
                    onCheckClicked = { onCheckClicked(todoItem.id, !todoItem.isComplete) },
                ) {
                    ListItem(
                        todoItem = todoItem,
                        onClicked = onItemClicked,
                        onCheckClicked = onCheckClicked,
                        onRequestFormattedTimestamp = onRequestFormattedTimestamp,
                    )
                }
            }

            item {
                NewListItem(onClicked = { onItemClicked(null) })
            }
        }
    }
}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
    onClicked: (String) -> Unit,
    onCheckClicked: (String, Boolean) -> Unit,
    onRequestFormattedTimestamp: (Long) -> String,
) {
    val appPalette = LocalAppPalette.current

    var isInfoDialogVisible by rememberSaveable { mutableStateOf(false) }

    val deadlineTimestamp = remember(todoItem) {
        todoItem.deadlineTimestamp?.let { deadlineTimestamp ->
            onRequestFormattedTimestamp(deadlineTimestamp)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = appPalette.backSecondary)
            .clickable { onClicked(todoItem.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        TodoCheckbox(
            isChecked = todoItem.isComplete,
            onCheckedChange = { checked -> onCheckClicked(todoItem.id, checked) },
            isImportant = todoItem.importance == TodoItemImportance.Urgent,
        )

        Spacer(modifier = Modifier.width(width = 12.dp))

        Row(
            modifier = Modifier
                .weight(weight = 1f),
        ) {
            if (todoItem.importance != TodoItemImportance.Normal && !todoItem.isComplete) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = if (todoItem.importance == TodoItemImportance.Urgent) {
                        AdditionalIcons.ImportanceHigh
                    } else {
                        AdditionalIcons.ImportanceLow
                    },
                    contentDescription = stringResource(id = R.string.importance),
                    tint = if (todoItem.importance == TodoItemImportance.Urgent) appPalette.colorRed else appPalette.colorGray,
                )
            }

            Column {
                Text(
                    text = todoItem.text,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = if (todoItem.isComplete) appPalette.labelTertiary else appPalette.labelPrimary,
                    style = if (todoItem.isComplete) AppTypography.bodyStrikethrough else AppTypography.body,
                )

                deadlineTimestamp?.let {
                    Spacer(modifier = Modifier.height(height = 4.dp))

                    Text(
                        text = deadlineTimestamp,
                        color = appPalette.labelTertiary,
                        style = AppTypography.subhead,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(width = 12.dp))

        Icon(
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable { isInfoDialogVisible = true },
            imageVector = Icons.Outlined.Info,
            contentDescription = stringResource(id = R.string.info),
            tint = appPalette.labelTertiary,
        )
    }

    TodoItemInfoDialog(
        isVisible = isInfoDialogVisible,
        onDismiss = { isInfoDialogVisible = false },
        todoItem = todoItem,
        onRequestFormattedTimestamp = onRequestFormattedTimestamp,
    )
}

@Composable
private fun NewListItem(
    onClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClicked)
            .padding(horizontal = 48.dp, vertical = 12.dp),
    ) {
        Text(
            text = stringResource(id = R.string.new_task),
            color = appPalette.labelTertiary,
            style = AppTypography.body,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ru")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
@Composable
private fun ListScreenPreview() {
    PreviewBase {
        ListScreen(
            lazyColumnState = rememberLazyListState(),
            topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state = rememberTopAppBarState()),
            isFabVisible = true,
            onFabVisibleChanged = {},
            isOnlyUncompletedVisible = false,
            onUncompletedVisibilityChanged = {},
            todoItems = ListScreenPreviewData.items,
            completedItemsCount = ListScreenPreviewData.items.count { it.isComplete },
            onItemClicked = {},
            onCheckClicked = { _, _ -> },
            onDeleteClicked = {},
            onRequestFormattedTimestamp = { "дата" },
        )
    }
}
