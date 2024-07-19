package com.voxeldev.todoapp.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.voxeldev.todoapp.about.compose.AboutScreen
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.auth.ui.AuthScreen
import com.voxeldev.todoapp.list.ui.ListScreen
import com.voxeldev.todoapp.settings.ui.SettingsScreen
import com.voxeldev.todoapp.task.ui.TaskScreen
import com.voxeldev.todoapp.ui.navigation.containers.rememberAboutScreenContainer
import com.voxeldev.todoapp.ui.navigation.containers.rememberAuthScreenContainer
import com.voxeldev.todoapp.ui.navigation.containers.rememberListScreenContainer
import com.voxeldev.todoapp.ui.navigation.containers.rememberSettingsScreenContainer
import com.voxeldev.todoapp.ui.navigation.containers.rememberTaskScreenContainer
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.StateFlow

private const val TRANSITION_DURATION_MILLIS = 300

/**
 * @author nvoxel
 */
internal fun NavGraphBuilder.aboutScreenComposable(
    navHostController: NavHostController,
) = composable(
    route = NavigationScreen.About.routeWithArguments,
    enterTransition = { enterTransition() },
    exitTransition = { exitTransition() },
) {
    AboutScreen(
        aboutScreenContainer = rememberAboutScreenContainer(),
        onClose = {
            navHostController.popBackStack(
                route = "${NavigationScreen.Settings.route!!}/true",
                inclusive = false,
            )
        },
    )
}

internal fun NavGraphBuilder.authScreenComposable(
    navHostController: NavHostController,
    onRequestOAuth: () -> Unit,
    onAuthSuccess: () -> Unit,
    authResultFlow: StateFlow<YandexAuthResult?>,
) = composable(
    route = NavigationScreen.Auth.routeWithArguments,
    enterTransition = { enterTransition(reverse = true) },
    exitTransition = { exitTransition(reverse = true) },
) {
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
    composable(
        route = NavigationScreen.List.routeWithArguments,
        enterTransition = { enterTransition(reverse = true) },
        exitTransition = { exitTransition(reverse = true) },
    ) {
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
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
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
) = composable(
    route = NavigationScreen.Settings.routeWithArguments,
    enterTransition = {
        enterTransition(reverse = initialState.destination.route != NavigationScreen.List.routeWithArguments)
    }, // false when navigating from list
    exitTransition = {
        exitTransition(
        reverse = navHostController.currentDestination?.route == NavigationScreen.About.routeWithArguments,
    )
    },
) {
    SettingsScreen(
        settingsScreenContainer = rememberSettingsScreenContainer(),
        onAbout = {
            navHostController.navigate(route = NavigationScreen.About.routeWithArguments)
        },
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

private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(reverse: Boolean = false) =
    slideIntoContainer(
        animationSpec = tween(TRANSITION_DURATION_MILLIS),
        towards = if (reverse) AnimatedContentTransitionScope.SlideDirection.End else AnimatedContentTransitionScope.SlideDirection.Start,
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(reverse: Boolean = false) =
    slideOutOfContainer(
        animationSpec = tween(TRANSITION_DURATION_MILLIS),
        towards = if (reverse) AnimatedContentTransitionScope.SlideDirection.Start else AnimatedContentTransitionScope.SlideDirection.End,
    )
