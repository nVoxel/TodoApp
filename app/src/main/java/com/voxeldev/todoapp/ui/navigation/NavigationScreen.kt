package com.voxeldev.todoapp.ui.navigation

/**
 * Represents app navigation screen.
 * @author nvoxel
 */
internal sealed class NavigationScreen(
    val route: String? = null,
    val routeWithArguments: String,
) {

    data object About : NavigationScreen(
        routeWithArguments = "about",
    )

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
        route = "settings",
        routeWithArguments = "settings/{$REVERSE_ARG}",
    )

    companion object {
        const val TASK_ID_ARG = "taskId"
        const val REVERSE_ARG = "reverse"
    }
}
