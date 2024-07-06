package com.voxeldev.todoapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.voxeldev.todoapp.auth.ui.AuthScreen
import com.voxeldev.todoapp.auth.viewmodel.AuthViewModel
import com.voxeldev.todoapp.list.ui.ListScreen
import com.voxeldev.todoapp.settings.ui.SettingsScreen
import com.voxeldev.todoapp.task.ui.TaskScreen
import com.voxeldev.todoapp.task.viewmodel.TaskViewModel
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.StateFlow

/**
 * @author nvoxel
 */
internal fun NavGraphBuilder.authScreenComposable(
    navHostController: NavHostController,
    onRequestOAuth: () -> Unit,
    onAuthSuccess: () -> Unit,
    authResultFlow: StateFlow<YandexAuthResult?>,
) = composable(route = NavigationScreen.Auth.routeWithArguments) {
    AuthScreen(
        onRequestOAuth = onRequestOAuth,
        onAuthSuccess = {
            navHostController.navigateToList()
            onAuthSuccess()
        },
        viewModel = hiltViewModel<AuthViewModel, AuthViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(yandexAuthResultFlow = authResultFlow)
            },
        ),
    )
}

internal fun NavGraphBuilder.listScreenComposable(navHostController: NavHostController) =
    composable(route = NavigationScreen.List.routeWithArguments) {
        ListScreen(
            onNavigateToTask = { taskId ->
                navHostController.navigate(route = "${NavigationScreen.Task.route!!}/$taskId")
            },
            onNavigateToSettings = {
                navHostController.navigate(route = NavigationScreen.Settings.routeWithArguments)
            },
            viewModel = hiltViewModel(),
        )
    }

internal fun NavGraphBuilder.taskScreenComposable(navHostController: NavHostController) =
    composable(
        route = NavigationScreen.Task.routeWithArguments,
        arguments = listOf(
            navArgument(name = NavigationScreen.TASK_ID_ARG) {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        TaskScreen(
            viewModel = hiltViewModel<TaskViewModel, TaskViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(taskId = it.arguments?.getString(NavigationScreen.TASK_ID_ARG))
                },
            ),
            onClose = { navHostController.popBackStack() },
        )
    }

internal fun NavGraphBuilder.settingsScreenComposable(navHostController: NavHostController) =
    composable(route = NavigationScreen.Settings.routeWithArguments) {
        SettingsScreen(
            viewModel = hiltViewModel(),
            onClose = { navHostController.popBackStack() },
            onLoggedOut = { navHostController.navigateToAuth() },
        )
    }
