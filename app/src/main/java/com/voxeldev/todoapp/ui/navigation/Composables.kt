package com.voxeldev.todoapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.auth.ui.AuthScreen
import com.voxeldev.todoapp.list.ui.ListScreen
import com.voxeldev.todoapp.settings.ui.SettingsScreen
import com.voxeldev.todoapp.task.ui.TaskScreen
import com.voxeldev.todoapp.ui.navigation.containers.rememberAuthScreenContainer
import com.voxeldev.todoapp.ui.navigation.containers.rememberListScreenContainer
import com.voxeldev.todoapp.ui.navigation.containers.rememberSettingsScreenContainer
import com.voxeldev.todoapp.ui.navigation.containers.rememberTaskScreenContainer
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
        authResultFlow = authResultFlow,
        authScreenContainer = rememberAuthScreenContainer(),
        onRequestOAuth = onRequestOAuth,
        onAuthSuccess = {
            navHostController.navigateToList()
            onAuthSuccess()
        },
    )
}

internal fun NavGraphBuilder.listScreenComposable(navHostController: NavHostController) =
    composable(route = NavigationScreen.List.routeWithArguments) {
        ListScreen(
            listScreenContainer = rememberListScreenContainer(),
            onNavigateToTask = { taskId ->
                navHostController.navigate(route = "${NavigationScreen.Task.route!!}/$taskId")
            },
            onNavigateToSettings = {
                navHostController.navigate(route = NavigationScreen.Settings.routeWithArguments)
            },
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
            taskId = it.arguments?.getString(NavigationScreen.TASK_ID_ARG),
            taskScreenContainer = rememberTaskScreenContainer(),
            onClose = {
                navHostController.popBackStack(
                    route = NavigationScreen.List.routeWithArguments,
                    inclusive = false,
                )
            },
        )
    }

internal fun NavGraphBuilder.settingsScreenComposable(
    navHostController: NavHostController,
    onThemeChanged: (AppTheme) -> Unit,
) = composable(route = NavigationScreen.Settings.routeWithArguments) {
    SettingsScreen(
        settingsScreenContainer = rememberSettingsScreenContainer(),
        onClose = {
            navHostController.popBackStack(
                route = NavigationScreen.List.routeWithArguments,
                inclusive = false,
            )
        },
        onLoggedOut = { navHostController.navigateToAuth() },
        onThemeChanged = onThemeChanged,
    )
}
