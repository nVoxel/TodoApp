package com.voxeldev.todoapp.test

import androidx.annotation.StringRes
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.voxeldev.todoapp.TestTodoApp
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.designsystem.components.TODO_TEXT_FIELD_TAG
import com.voxeldev.todoapp.designsystem.theme.TodoAppTheme
import com.voxeldev.todoapp.list.ui.TASK_LIST_TAG
import com.voxeldev.todoapp.ui.AndroidTestActivity
import com.voxeldev.todoapp.ui.navigation.MainNavHost
import com.voxeldev.todoapp.ui.navigation.rememberNavigationContainer
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test
import com.voxeldev.todoapp.designsystem.R as DesignRes
import com.voxeldev.todoapp.list.R as ListRes
import com.voxeldev.todoapp.task.R as TaskRes

/**
 * @author nvoxel
 */
class ListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<AndroidTestActivity>()

    @After
    fun tearDown() {
        runBlocking { // reset the list after running each test
            (composeTestRule.activity.application as TestTodoApp).todoItemRepository.clearLocalData()
        }
    }

    @Test
    fun taskItemExists() {
        val taskText = "First task"

        composeTestRule.setAppContent()

        composeTestRule
            .onNodeWithTag(testTag = TASK_LIST_TAG)
            .onChildAt(index = 1)
            .assertTextEquals(taskText)
    }

    @Test
    fun taskItemAdded() {
        val taskText = "Second task"

        composeTestRule.setAppContent()

        composeTestRule.onNodeWithContentDescription(
            label = getStringRes(id = DesignRes.string.add_task),
        ).performClick()

        composeTestRule.onNodeWithTag(testTag = TODO_TEXT_FIELD_TAG).performTextInput(taskText)

        composeTestRule.onNodeWithText(text = getStringRes(id = TaskRes.string.create).uppercase()).performClick()

        composeTestRule
            .onNodeWithTag(testTag = TASK_LIST_TAG)
            .onChildAt(index = 2)
            .assertTextEquals(taskText)
    }

    @Test
    fun taskItemUpdated() {
        val taskTextBefore = "First task"
        val taskTextAfter = "Second task"

        composeTestRule.setAppContent()

        composeTestRule.onNodeWithText(text = taskTextBefore).performClick()

        composeTestRule.onNodeWithTag(testTag = TODO_TEXT_FIELD_TAG).performTextReplacement(taskTextAfter)

        composeTestRule.onNodeWithText(text = getStringRes(id = TaskRes.string.save).uppercase()).performClick()

        composeTestRule
            .onNodeWithTag(testTag = TASK_LIST_TAG)
            .onChildAt(index = 1)
            .assertTextEquals(taskTextAfter)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun taskItemDeleted() {
        val taskText = "First task"

        composeTestRule.setAppContent()

        composeTestRule.onNodeWithText(text = taskText).performClick()

        composeTestRule.onNodeWithText(text = getStringRes(id = TaskRes.string.delete)).performClick()

        composeTestRule.waitUntilDoesNotExist(hasText(text = getStringRes(id = TaskRes.string.delete_message)))

        composeTestRule
            .onNodeWithTag(testTag = TASK_LIST_TAG)
            .onChildAt(index = 1)
            .assertTextEquals(getStringRes(id = ListRes.string.new_task))
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<AndroidTestActivity>, AndroidTestActivity>.setAppContent() {
        val successfulAuthResult = YandexAuthResult.Success(
            token = YandexAuthToken(
                value = "token",
                expiresIn = 0L,
            ),
        )

        setContent {
            TodoAppTheme(appTheme = AppTheme.Auto) {
                MainNavHost(
                    navigationContainer = rememberNavigationContainer(),
                    authResultFlow = MutableStateFlow(value = successfulAuthResult),
                    onRequestOAuth = { },
                    onAuthSuccess = { },
                    onThemeChanged = { },
                )
            }
        }
    }

    private fun getStringRes(@StringRes id: Int) = composeTestRule.activity.getString(id)
}
