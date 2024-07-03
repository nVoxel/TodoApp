package com.voxeldev.todoapp.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.voxeldev.todoapp.auth.ui.AuthScreen
import com.voxeldev.todoapp.designsystem.components.FullscreenLoader
import com.voxeldev.todoapp.list.ui.ListScreen
import com.voxeldev.todoapp.settings.ui.SettingsScreen
import com.voxeldev.todoapp.task.ui.TaskScreen
import com.voxeldev.todoapp.task.viewmodel.TaskViewModel

/**
 * @author nvoxel
 */
@Composable
internal fun MainNavHost(
    navHostController: NavHostController = rememberNavController(),
) {
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val authTokenState by navigationViewModel.authTokenState.collectAsStateWithLifecycle()

    if (authTokenState is AuthTokenState.Loading) {
        FullscreenLoader()
    } else {
        MainNavHost(
            navHostController = navHostController,
            startDestination = if (authTokenState is AuthTokenState.Found) NavigationScreen.List else NavigationScreen.Auth,
        )
    }
}

@Composable
private fun MainNavHost(
    navHostController: NavHostController,
    startDestination: NavigationScreen,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination.routeWithArguments,
        enterTransition = {
            slideInHorizontally { fullWidth ->
                if (navHostController.currentDestination?.route == NavigationScreen.List.routeWithArguments) 0 else fullWidth
            }
        },
        exitTransition = {
            slideOutHorizontally { fullWidth ->
                if (
                    navHostController.currentDestination?.route == NavigationScreen.Task.routeWithArguments ||
                    navHostController.currentDestination?.route == NavigationScreen.Settings.routeWithArguments
                ) {
                    0
                } else {
                    fullWidth
                }
            }
        },
    ) {
        composable(route = NavigationScreen.Auth.routeWithArguments) {
            AuthScreen(
                onAuthSuccess = { navHostController.navigateToList() },
                viewModel = hiltViewModel(),
            )
        }

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

        composable(route = NavigationScreen.Settings.routeWithArguments) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                onClose = { navHostController.popBackStack() },
                onLoggedOut = { navHostController.navigateToAuth() },
            )
        }
    }
}

private fun NavHostController.navigateToAuth() = navigate(
    route = NavigationScreen.Auth.routeWithArguments,
    navOptions {
        popUpTo(route = NavigationScreen.List.routeWithArguments) {
            inclusive = true
        }
    },
)

private fun NavHostController.navigateToList() = navigate(
    route = NavigationScreen.List.routeWithArguments,
    navOptions {
        popUpTo(route = NavigationScreen.Auth.routeWithArguments) {
            inclusive = true
        }
    },
)
