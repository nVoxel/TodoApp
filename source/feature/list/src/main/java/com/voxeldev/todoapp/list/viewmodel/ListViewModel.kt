package com.voxeldev.todoapp.list.viewmodel

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.UpdateTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.extensions.formatTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
) : BaseViewModel() {

    private var _todoItems: MutableStateFlow<List<TodoItem>> = MutableStateFlow(value = emptyList())
    val todoItems: StateFlow<List<TodoItem>> = _todoItems

    private val _completedItemsCount: MutableStateFlow<Int> = MutableStateFlow(value = 0)
    val completedItemsCount: StateFlow<Int> = _completedItemsCount

    private val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        getTodoItems()
    }

    fun getTodoItems() {
        _loading.value = true
        _exception.value = null

        getAllTodoItemsFlowUseCase(
            params = BaseUseCase.NoParams,
            scope = scope,
        ) { result ->
            result.fold(
                onSuccess = { flow ->
                    scope.launch {
                        flow.collect { todoItems ->
                            _todoItems.value = todoItems
                            _completedItemsCount.value = todoItems.count { it.isComplete }
                        }
                    }
                    _loading.value = false
                },
                onFailure = ::handleException,
            )
        }
    }

    fun checkTodoItem(id: String, isChecked: Boolean) {
        val item = todoItems.value.find { item -> item.id == id }
        item?.let {
            // not used for stub repository
            // _loading.value = true

            val newItem = item.copy(isComplete = isChecked)
            updateTodoItemUseCase(
                params = newItem,
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = { /*_loading.value = false*/ },
                    onFailure = ::handleException,
                )
            }
        }
    }

    fun deleteTodoItem(id: String) {
        // not used for stub repository
        // _loading.value = true

        deleteTodoItemUseCase(
            params = id,
            scope = scope,
        ) { result ->
            result.fold(
                onSuccess = { /*_loading.value = false*/ },
                onFailure = ::handleException,
            )
        }
    }

    fun getFormattedTimestamp(timestamp: Long): String =
        timestamp.formatTimestamp(format = format)
}
