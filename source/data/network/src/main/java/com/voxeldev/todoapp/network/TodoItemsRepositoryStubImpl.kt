package com.voxeldev.todoapp.network

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.api.repository.TodoItemsRepository
import com.voxeldev.todoapp.utils.exceptions.ItemNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * @author nvoxel
 */
class TodoItemsRepositoryStubImpl @Inject constructor() : TodoItemsRepository {

    override fun createItem(item: TodoItem): Result<Unit> = runCatching {
        todoItemsFlow.value += item
    }

    override fun getAllFlow(): Result<Flow<List<TodoItem>>> {
        return Result.success(todoItemsFlow)
    }

    override fun getSingle(id: String): Result<TodoItem> = runCatching {
        todoItemsFlow.value.firstOrNull { item -> item.id == id } ?: throw ItemNotFoundException(id = id)
    }

    override fun updateItem(item: TodoItem): Result<Unit> = runCatching {
        todoItemsFlow.value = todoItemsFlow.value.map { listItem -> if (listItem.id == item.id) item else listItem }
    }

    override fun deleteItem(id: String): Result<Unit> = runCatching {
        todoItemsFlow.value = todoItemsFlow.value.filter { listItem -> listItem.id != id }
    }

    // Items stub
    private val todoItemsFlow = MutableStateFlow(
        listOf(
            TodoItem(
                id = "1",
                text = "Завершить чтение документации к новому обновлению программного обеспечения.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = 1716175200,
                isComplete = false,
                creationTimestamp = 1715930496,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "2",
                text = "Запланировать тимбилдинговую деятельность на следующий квартал.",
                importance = TodoItemImportance.Urgent,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1715951200,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "3",
                text = "Отправить обновленное проектное предложение клиенту на рассмотрение.",
                importance = TodoItemImportance.Low,
                deadlineTimestamp = null,
                isComplete = true,
                creationTimestamp = 1715972800,
                modifiedTimestamp = 1716036800,
            ),
            TodoItem(
                id = "4",
                text = "Запланировать встречу с маркетинговой командой для обсуждения новой кампании. Оооооооооочень длинный текст.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = 1716261600,
                isComplete = false,
                creationTimestamp = 1715994400,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "5",
                text = "Рассмотреть квартальный финансовый отчет и предоставить обратную связь.",
                importance = TodoItemImportance.Urgent,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1716016000,
                modifiedTimestamp = 1716080000,
            ),
            TodoItem(
                id = "6",
                text = "Обновить сайт компании последними новостями и событиями.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = 1716348000,
                isComplete = true,
                creationTimestamp = 1716037600,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "7",
                text = "Организовать офисное пространство для повышения эффективности и производительности.",
                importance = TodoItemImportance.Low,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1716059200,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "8",
                text = "Подготовить презентацию к предстоящей отраслевой конференции.",
                importance = TodoItemImportance.Urgent,
                deadlineTimestamp = 1716434400,
                isComplete = false,
                creationTimestamp = 1716080800,
                modifiedTimestamp = 1716144800,
            ),
            TodoItem(
                id = "9",
                text = "Собрать отзывы пользователей о недавнем запуске продукта. Оооооооооочень длинный текст.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1716102400,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "10",
                text = "Провести анализ эффективности работы всех членов команды.",
                importance = TodoItemImportance.Urgent,
                deadlineTimestamp = 1716520800,
                isComplete = true,
                creationTimestamp = 1716124000,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "11",
                text = "Разработать новую политику удаленной работы и работы на дому.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1716145600,
                modifiedTimestamp = 1716209600,
            ),
            TodoItem(
                id = "12",
                text = "Проанализировать конкуренцию для выявления потенциальных рыночных возможностей.",
                importance = TodoItemImportance.Low,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1716167200,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "13",
                text = "Провести мозговой штурм для генерации идей новых продуктов.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = 1716607200,
                isComplete = true,
                creationTimestamp = 1716188800,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "14",
                text = "Подготовить бюджет на следующий финансовый год.",
                importance = TodoItemImportance.Urgent,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1716210400,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "15",
                text = "Обновить справочник сотрудников новейшими политиками и процедурами.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = 1716693600,
                isComplete = true,
                creationTimestamp = 1716232000,
                modifiedTimestamp = 1716296000,
            ),
            TodoItem(
                id = "16",
                text = "Запланировать последующую встречу с клиентом для обсуждения хода проекта. Ооооооооочень длинный текст.",
                importance = TodoItemImportance.Urgent,
                deadlineTimestamp = null,
                isComplete = true,
                creationTimestamp = 1716253600,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "17",
                text = "Исследовать новые программные инструменты для улучшения совместной работы в команде.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = null,
                isComplete = false,
                creationTimestamp = 1716275200,
                modifiedTimestamp = 1716339200,
            ),
            TodoItem(
                id = "18",
                text = "Запланировать общекорпоративное обучение работе с новой CRM-системой.",
                importance = TodoItemImportance.Low,
                deadlineTimestamp = 1716780000,
                isComplete = false,
                creationTimestamp = 1716296800,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "19",
                text = "Разработать опрос для сбора данных об удовлетворенности сотрудников.",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = null,
                isComplete = true,
                creationTimestamp = 1716318400,
                modifiedTimestamp = null,
            ),
            TodoItem(
                id = "20",
                text = "Разработать маркетинговую стратегию для запуска нового продукта. Оооооооооооочень длинный текст.",
                importance = TodoItemImportance.Urgent,
                deadlineTimestamp = 1716866400,
                isComplete = false,
                creationTimestamp = 1716340000,
                modifiedTimestamp = 1716404000,
            ),
        ),
    )
}
