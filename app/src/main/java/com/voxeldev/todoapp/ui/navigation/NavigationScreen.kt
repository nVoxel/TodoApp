package com.voxeldev.todoapp.ui.navigation

/**
 * @author nvoxel
 */
internal sealed class NavigationScreen(
    val route: String? = null,
    val routeWithArguments: String,
) {

    data object Auth : NavigationScreen(
        routeWithArguments = "auth",
    )

    data object List : NavigationScreen(
        routeWithArguments = "list",
    )

    data object Task : NavigationScreen(
        route = "task",
        routeWithArguments = "task/{$TASK_ID_ARG}",
    )

    data object Settings : NavigationScreen(
        routeWithArguments = "settings",
    )

    companion object {
        const val TASK_ID_ARG = "taskId"
    }
}
