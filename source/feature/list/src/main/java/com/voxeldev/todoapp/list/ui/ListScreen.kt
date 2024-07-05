package com.voxeldev.todoapp.list.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.designsystem.components.ErrorSnackbarEffect
import com.voxeldev.todoapp.designsystem.components.TodoSmallFAB
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.list.ui.components.ListItem
import com.voxeldev.todoapp.list.ui.components.ListScreenTopBar
import com.voxeldev.todoapp.list.ui.components.NewListItem
import com.voxeldev.todoapp.list.ui.components.SwipeableListItem
import com.voxeldev.todoapp.list.ui.preview.ListScreenPreviewData
import com.voxeldev.todoapp.list.viewmodel.ListViewModel
import com.voxeldev.todoapp.utils.extensions.getDisplayMessage

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onNavigateToTask: (String?) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: ListViewModel,
) {
    val lazyColumnState = rememberLazyListState()
    val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state = rememberTopAppBarState())

    var isFabVisible by rememberSaveable { mutableStateOf(true) }
    var isOnlyUncompletedVisible by rememberSaveable { mutableStateOf(false) }

    val todoItems by viewModel.todoItems.collectAsStateWithLifecycle()
    val completedItemsCount by viewModel.completedItemsCount.collectAsStateWithLifecycle()
    val error by viewModel.exception.collectAsStateWithLifecycle()

    val displayFullscreenError = todoItems.isEmpty() // don't display after initial load

    BaseScreen(
        viewModel = viewModel,
        retryCallback = viewModel::getTodoItems,
        displayFullscreenError = displayFullscreenError,
    ) {
        ListScreen(
            lazyColumnState = lazyColumnState,
            topBarScrollBehavior = topBarScrollBehavior,
            isFabVisible = isFabVisible,
            error = error?.getDisplayMessage(),
            onSnackbarHide = viewModel::onSnackbarHide,
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
            onSettingsClicked = onNavigateToSettings,
            onRequestFormattedTimestamp = viewModel::getFormattedTimestamp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ListScreen(
    lazyColumnState: LazyListState,
    topBarScrollBehavior: TopAppBarScrollBehavior,
    isFabVisible: Boolean,
    error: String?,
    onSnackbarHide: () -> Unit,
    onFabVisibleChanged: (Boolean) -> Unit,
    isOnlyUncompletedVisible: Boolean,
    onUncompletedVisibilityChanged: (Boolean) -> Unit,
    todoItems: List<TodoItem>,
    completedItemsCount: Int,
    onItemClicked: (String?) -> Unit,
    onCheckClicked: (String, Boolean) -> Unit,
    onDeleteClicked: (String) -> Unit,
    onSettingsClicked: () -> Unit,
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

    val snackbarHostState = remember { SnackbarHostState() }

    ErrorSnackbarEffect(
        errorMessage = error,
        snackbarHostState = snackbarHostState,
        onHide = onSnackbarHide,
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(connection = fabNestedScrollConnection),
        topBar = {
            ListScreenTopBar(
                topBarScrollBehavior = topBarScrollBehavior,
                completedItemsCount = completedItemsCount,
                isOnlyUncompletedVisible = isOnlyUncompletedVisible,
                onUncompletedVisibilityChanged = onUncompletedVisibilityChanged,
                onSettingsClicked = onSettingsClicked,
            )
        },
        floatingActionButton = {
            TodoSmallFAB(
                isFabVisible = isFabVisible,
                onClick = { onItemClicked(null) },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = appPalette.backPrimary,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 4.dp)
                .navigationBarsPadding()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(size = 8.dp),
                )
                .background(
                    color = appPalette.backSecondary,
                    shape = RoundedCornerShape(size = 8.dp),
                ),
            state = lazyColumnState,
            contentPadding = PaddingValues(vertical = 8.dp),
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
                NewListItem(
                    modifier = Modifier.animateItemPlacement(),
                    onClicked = { onItemClicked(null) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ScreenDayNightPreviews
@Composable
private fun ListScreenPreview() {
    PreviewBase {
        ListScreen(
            lazyColumnState = rememberLazyListState(),
            topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state = rememberTopAppBarState()),
            isFabVisible = true,
            error = null,
            onSnackbarHide = {},
            onFabVisibleChanged = {},
            isOnlyUncompletedVisible = false,
            onUncompletedVisibilityChanged = {},
            todoItems = ListScreenPreviewData.items,
            completedItemsCount = ListScreenPreviewData.items.count { it.isComplete },
            onItemClicked = {},
            onCheckClicked = { _, _ -> },
            onDeleteClicked = {},
            onSettingsClicked = {},
            onRequestFormattedTimestamp = { "дата" },
        )
    }
}
