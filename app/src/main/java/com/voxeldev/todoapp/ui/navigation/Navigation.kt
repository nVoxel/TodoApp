package com.voxeldev.todoapp.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.voxeldev.todoapp.list.ui.ListScreen
import com.voxeldev.todoapp.task.ui.TaskScreen
import com.voxeldev.todoapp.task.viewmodel.TaskViewModel

/**
 * @author nvoxel
 */
@Composable
internal fun MainNavHost(
    navHostController: NavHostController = rememberNavController(),
    startDestination: NavigationScreen = NavigationScreen.List,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination.routeWithArguments,
        enterTransition = {
            slideInHorizontally {
                if (navHostController.currentDestination?.route == NavigationScreen.List.routeWithArguments) 0 else it
            }
        },
        exitTransition = {
            slideOutHorizontally {
                if (navHostController.currentDestination?.route == NavigationScreen.Task.routeWithArguments) 0 else it
            }
        },
    ) {
        composable(route = NavigationScreen.List.routeWithArguments) {
            ListScreen(
                onNavigateToTask = { taskId ->
                    navHostController.navigate(route = "${NavigationScreen.Task.route!!}/$taskId")
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
    }
}
