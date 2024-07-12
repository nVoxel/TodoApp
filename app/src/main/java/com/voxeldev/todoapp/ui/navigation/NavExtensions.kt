package com.voxeldev.todoapp.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.navOptions

/**
 * @author nvoxel
 */
internal fun NavHostController.navigateToAuth() = navigate(
    route = NavigationScreen.Auth.routeWithArguments,
    navOptions {
        popUpTo(route = NavigationScreen.List.routeWithArguments) {
            inclusive = true
        }
    },
)

internal fun NavHostController.navigateToList() = navigate(
    route = NavigationScreen.List.routeWithArguments,
    navOptions {
        popUpTo(route = NavigationScreen.Auth.routeWithArguments) {
            inclusive = true
        }
    },
)
