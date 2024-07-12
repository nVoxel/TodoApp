package com.voxeldev.todoapp.list.ui.preview

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance

/**
 * @author nvoxel
 */
internal object ListScreenPreviewData {
    val items = listOf(
        TodoItem(
            id = "1",
            text = "Завершить чтение документации к новому обновлению программного обеспечения.",
            importance = TodoItemImportance.Normal,
            deadlineTimestamp = 1716175200,
            isComplete = false,
            creationTimestamp = 1715930496,
            modifiedTimestamp = 1715930496,
        ),
        TodoItem(
            id = "2",
            text = "Купить",
            importance = TodoItemImportance.Urgent,
            deadlineTimestamp = null,
            isComplete = false,
            creationTimestamp = 1715951200,
            modifiedTimestamp = 1715951200,
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
            modifiedTimestamp = 1715994400,
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
            modifiedTimestamp = 1716037600,
        ),
        TodoItem(
            id = "7",
            text = "Организовать офисное пространство для повышения эффективности и производительности.",
            importance = TodoItemImportance.Low,
            deadlineTimestamp = null,
            isComplete = false,
            creationTimestamp = 1716059200,
            modifiedTimestamp = 1716059200,
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
            modifiedTimestamp = 1716102400,
        ),
        TodoItem(
            id = "10",
            text = "Провести анализ эффективности работы всех членов команды.",
            importance = TodoItemImportance.Urgent,
            deadlineTimestamp = 1716520800,
            isComplete = true,
            creationTimestamp = 1716124000,
            modifiedTimestamp = 1716124000,
        ),
    )
}
