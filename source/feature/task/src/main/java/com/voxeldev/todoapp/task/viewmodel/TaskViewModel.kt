package com.voxeldev.todoapp.task.viewmodel

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.domain.usecase.CreateTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.GetSingleTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.UpdateTodoItemUseCase
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.extensions.formatTimestamp
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

/**
 * @author nvoxel
 */
@HiltViewModel(assistedFactory = TaskViewModel.Factory::class)
class TaskViewModel @AssistedInject constructor(
    @Assisted val taskId: String?,
    @Assisted val scopeDispatcher: CoroutineDispatcher,
    private val createTodoItemUseCase: CreateTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val getSingleTodoItemUseCase: GetSingleTodoItemUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
) : BaseViewModel() {

    private val _text: MutableStateFlow<String> = MutableStateFlow(value = "")
    val text: StateFlow<String> = _text

    private val _importance: MutableStateFlow<TodoItemImportance> = MutableStateFlow(value = TodoItemImportance.Normal)
    val importance: StateFlow<TodoItemImportance> = _importance.asStateFlow()

    private val _deadlineTimestamp: MutableStateFlow<Long?> = MutableStateFlow(value = null)
    val deadlineTimestamp: StateFlow<Long?> = _deadlineTimestamp.asStateFlow()

    private val _deadlineTimestampString: MutableStateFlow<String?> = MutableStateFlow(value = null)
    val deadlineTimestampString: StateFlow<String?> = _deadlineTimestampString.asStateFlow()

    private val _saveButtonActive: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val saveButtonActive: StateFlow<Boolean> = _saveButtonActive.asStateFlow()

    private var loadedTodoItem: TodoItem? = null

    private val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

    private val scope = CoroutineScope(SupervisorJob() + scopeDispatcher)

    @AssistedFactory
    interface Factory {
        fun create(
            taskId: String?,
            scopeDispatcher: CoroutineDispatcher = Dispatchers.IO,
        ): TaskViewModel
    }

    init {
        getTodoItem()
    }

    fun getTodoItem() {
        taskId?.let {
            _loading.update { true }
            _exception.update { null }

            getSingleTodoItemUseCase(
                params = taskId,
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = { todoItem ->
                        updateText(text = todoItem.text)
                        updateImportance(importance = todoItem.importance)
                        todoItem.deadlineTimestamp?.let {
                            updateDeadlineTimestamp(deadlineTimestamp = it)
                        }
                        loadedTodoItem = todoItem
                        _loading.update { false }
                    },
                    onFailure = ::handleException,
                )
            }
        }
    }

    fun updateText(text: String) {
        _text.update { text }
        updateSaveButton()
    }

    fun updateImportance(importance: TodoItemImportance) {
        _importance.update { importance }
    }

    fun updateDeadlineTimestamp(deadlineTimestamp: Long) {
        _deadlineTimestamp.update { deadlineTimestamp }
        _deadlineTimestampString.update { deadlineTimestamp.formatTimestamp(format = format) }
    }

    fun resetDeadlineTimestamp() {
        _deadlineTimestamp.update { null }
        _deadlineTimestampString.update { null }
    }

    private fun updateSaveButton() {
        _saveButtonActive.update { canSaveItem() }
    }

    fun saveItem(callback: () -> Unit) {
        if (!canSaveItem()) return

        loadedTodoItem?.let {
            updateItem(callback = callback)
        } ?: createItem(callback = callback)
    }

    private fun createItem(callback: () -> Unit) {
        val newItem = TodoItem(
            id = UUID.randomUUID().toString(),
            text = _text.value,
            importance = _importance.value,
            deadlineTimestamp = _deadlineTimestamp.value,
            isComplete = false,
            creationTimestamp = getTimestamp(),
            modifiedTimestamp = null,
        )

        _loading.update { true }
        createTodoItemUseCase(
            params = newItem,
            scope = scope,
        ) { result ->
            result.fold(
                onSuccess = {
                    _loading.update { false }
                    callback()
                },
                onFailure = ::handleException,
            )
        }
    }

    private fun updateItem(callback: () -> Unit) {
        val updatedItem = loadedTodoItem?.copy(
            text = _text.value,
            importance = _importance.value,
            deadlineTimestamp = _deadlineTimestamp.value,
            modifiedTimestamp = getTimestamp(),
        )

        updatedItem?.let {
            _loading.update { true }
            updateTodoItemUseCase(
                params = updatedItem,
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = {
                        _loading.update { false }
                        callback()
                    },
                    onFailure = ::handleException,
                )
            }
        }
    }

    fun deleteItem(callback: () -> Unit) {
        loadedTodoItem?.let { todoItem ->
            _loading.update { true }
            deleteTodoItemUseCase(
                params = todoItem.id,
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = {
                        _loading.update { false }
                        callback()
                    },
                    onFailure = ::handleException,
                )
            }
        }
    }

    private fun getTimestamp() = System.currentTimeMillis() / 1000

    private fun canSaveItem(): Boolean = _text.value.isNotBlank()

    override fun onCleared() {
        scope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}
