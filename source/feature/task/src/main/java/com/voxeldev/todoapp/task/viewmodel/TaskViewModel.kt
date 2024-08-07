package com.voxeldev.todoapp.task.viewmodel

import com.voxeldev.todoapp.api.extensions.toModifyRequest
import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.domain.usecase.todoitem.CreateTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetSingleTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.task.ui.TaskScreen
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.extensions.formatTimestamp
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.voxeldev.todoapp.utils.providers.UUIDProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Stores [TaskScreen] current state, provides screen-related methods.
 * @author nvoxel
 */
class TaskViewModel(
    private val taskId: String?,
    private val createTodoItemUseCase: CreateTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val getSingleTodoItemUseCase: GetSingleTodoItemUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    networkObserver: NetworkObserver,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    networkObserver = networkObserver,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

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

    init {
        getTodoItem()

        scope.launch {
            networkObserver.networkAvailability.collect { networkAvailable ->
                if (networkAvailable) getTodoItem()
            }
        }
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

    fun onSnackbarHide() {
        _exception.update { null }
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
        val timestamp = getTimestamp()

        val request = TodoItemModifyRequest(
            id = UUIDProvider.provideUUID(),
            text = _text.value,
            importance = _importance.value,
            deadlineTimestamp = _deadlineTimestamp.value,
            isComplete = false,
            creationTimestamp = timestamp,
            modifiedTimestamp = timestamp,
        )

        _loading.update { true }
        createTodoItemUseCase(
            params = request,
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
        val request = loadedTodoItem?.copy(
            text = _text.value,
            importance = _importance.value,
            deadlineTimestamp = _deadlineTimestamp.value,
            modifiedTimestamp = getTimestamp(),
        )?.toModifyRequest()

        request?.let {
            _loading.update { true }
            updateTodoItemUseCase(
                params = request,
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
}
