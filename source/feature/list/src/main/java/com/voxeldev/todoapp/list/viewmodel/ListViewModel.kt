package com.voxeldev.todoapp.list.viewmodel

import com.voxeldev.todoapp.api.extensions.toModifyRequest
import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.RefreshTodoItemsUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.list.ui.ListScreen
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.extensions.formatTimestamp
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Stores [ListScreen] current state, provides methods to change [TodoItem].
 * @author nvoxel
 */
class ListViewModel(
    private val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val refreshTodoItemsUseCase: RefreshTodoItemsUseCase,
    private val networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    networkObserver = networkObserver,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val _todoItems: MutableStateFlow<TodoItemList> = MutableStateFlow(
        value = TodoItemList(
            list = emptyList(),
            isOffline = false,
        ),
    )
    val todoItems: StateFlow<TodoItemList> = _todoItems.asStateFlow()

    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(value = 0)
    val completedItemsCount: StateFlow<Int> = _completedItemsCount.asStateFlow()

    private val _listLoaderVisible: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val listLoaderVisible: StateFlow<Boolean> = _listLoaderVisible.asStateFlow()

    private val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

    init {
        getTodoItems()

        scope.launch {
            networkObserver.networkAvailability.collect { networkAvailable ->
                if (!networkAvailable || !todoItems.value.isOffline) return@collect
                refreshTodoItemsUseCase(
                    params = BaseUseCase.NoParams,
                    scope = scope,
                ) { result ->
                    result.onFailure(action = ::handleException)
                }
            }
        }
    }

    fun onSnackbarHide() {
        _exception.update { null }
    }

    fun getTodoItems() {
        _loading.update { true }
        _exception.update { null }

        getAllTodoItemsFlowUseCase(
            params = BaseUseCase.NoParams,
            scope = scope,
        ) { result ->
            result.fold(
                onSuccess = { flow ->
                    scope.launch {
                        flow.collect { todoItems ->
                            _todoItems.update { todoItems }
                            _completedItemsCount.update { todoItems.list.count { it.isComplete } }
                        }
                    }
                    _loading.update { false }
                },
                onFailure = ::handleException,
            )
        }
    }

    fun checkTodoItem(id: String, isChecked: Boolean) {
        val item = todoItems.value.list.find { item -> item.id == id }
        item?.let {
            _listLoaderVisible.update { true }

            updateTodoItemUseCase(
                params = item.copy(
                    isComplete = isChecked,
                    modifiedTimestamp = getTimestamp(),
                ).toModifyRequest(),
                scope = scope,
            ) { result ->
                result.onFailure(action = ::handleException)
                _listLoaderVisible.update { false }
            }
        }
    }

    fun deleteTodoItem(id: String) {
        _listLoaderVisible.update { true }

        deleteTodoItemUseCase(
            params = id,
            scope = scope,
        ) { result ->
            result.onFailure(action = ::handleException)
            _listLoaderVisible.update { false }
        }
    }

    fun getFormattedTimestamp(timestamp: Long): String =
        timestamp.formatTimestamp(format = format)

    private fun getTimestamp() = System.currentTimeMillis() / 1000
}
