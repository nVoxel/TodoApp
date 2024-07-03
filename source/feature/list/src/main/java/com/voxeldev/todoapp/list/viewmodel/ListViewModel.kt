package com.voxeldev.todoapp.list.viewmodel

import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.extensions.formatTimestamp
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * @author nvoxel
 */
@HiltViewModel
class ListViewModel @Inject constructor(
    private val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    networkObserver = networkObserver,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val _todoItems: MutableStateFlow<TodoItemList> = MutableStateFlow(value = emptyList())
    val todoItems: StateFlow<TodoItemList> = _todoItems.asStateFlow()

    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(value = 0)
    val completedItemsCount: StateFlow<Int> = _completedItemsCount.asStateFlow()

    private val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

    init {
        getTodoItems()
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
                            _completedItemsCount.update { todoItems.count { it.isComplete } }
                        }
                    }
                    _loading.update { false }
                },
                onFailure = ::handleException,
            )
        }
    }

    fun checkTodoItem(id: String, isChecked: Boolean) {
        val item = todoItems.value.find { item -> item.id == id }
        item?.let {
            val newItem = item.copy(isComplete = isChecked)
            updateTodoItemUseCase(
                params = newItem,
                scope = scope,
            ) { result ->
                result.onFailure(action = ::handleException)
            }
        }
    }

    fun deleteTodoItem(id: String) {
        deleteTodoItemUseCase(
            params = id,
            scope = scope,
        ) { result ->
            result.onFailure(action = ::handleException)
        }
    }

    fun getFormattedTimestamp(timestamp: Long): String =
        timestamp.formatTimestamp(format = format)

    override fun onNetworkConnected() = getTodoItems()
}
