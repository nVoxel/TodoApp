package com.voxeldev.todoapp.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.voxeldev.todoapp.auth.ui.AuthScreen
import com.voxeldev.todoapp.auth.viewmodel.AuthViewModelProvider
import com.voxeldev.todoapp.list.ui.ListScreen
import com.voxeldev.todoapp.list.viewmodel.ListViewModelProvider
import com.voxeldev.todoapp.settings.ui.SettingsScreen
import com.voxeldev.todoapp.settings.viewmodel.SettingsViewModelProvider
import com.voxeldev.todoapp.task.ui.TaskScreen
import com.voxeldev.todoapp.task.viewmodel.TaskViewModelProvider
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.StateFlow

/**
 * @author nvoxel
 */
internal fun NavGraphBuilder.authScreenComposable(
    authViewModelProviderFactory: AuthViewModelProvider.Factory,
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
        viewModel = viewModel(
            factory = authViewModelProviderFactory.create(
                yandexAuthResultFlow = authResultFlow,
            ),
        ),
    )
}

internal fun NavGraphBuilder.listScreenComposable(
    listViewModelProvider: ListViewModelProvider,
    navHostController: NavHostController,
) = composable(route = NavigationScreen.List.routeWithArguments) {
    ListScreen(
        onNavigateToTask = { taskId ->
            navHostController.navigate(route = "${NavigationScreen.Task.route!!}/$taskId")
        },
        onNavigateToSettings = {
            navHostController.navigate(route = NavigationScreen.Settings.routeWithArguments)
        },
        viewModel = viewModel(factory = listViewModelProvider),
    )
}

internal fun NavGraphBuilder.taskScreenComposable(
    taskViewModelProviderFactory: TaskViewModelProvider.Factory,
    navHostController: NavHostController,
) = composable(
        route = NavigationScreen.Task.routeWithArguments,
        arguments = listOf(
            navArgument(name = NavigationScreen.TASK_ID_ARG) {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        TaskScreen(
            viewModel = viewModel(
                factory = taskViewModelProviderFactory.create(
                    taskId = it.arguments?.getString(NavigationScreen.TASK_ID_ARG),
                ),
            ),
            onClose = { navHostController.popBackStack() },
        )
    }

internal fun NavGraphBuilder.settingsScreenComposable(
    settingsViewModelProvider: SettingsViewModelProvider,
    navHostController: NavHostController,
) =
    composable(route = NavigationScreen.Settings.routeWithArguments) {
        SettingsScreen(
            viewModel = viewModel(factory = settingsViewModelProvider),
            onClose = { navHostController.popBackStack() },
            onLoggedOut = { navHostController.navigateToAuth() },
        )
    }
