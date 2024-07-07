package com.voxeldev.todoapp.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.voxeldev.todoapp.designsystem.components.FullscreenLoader
import com.voxeldev.todoapp.di.navigation.NavigationContainer
import com.voxeldev.todoapp.ui.navigation.state.AuthTokenState
import com.voxeldev.todoapp.ui.viewmodel.navigation.NavigationViewModel
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.StateFlow

/**
 * @author nvoxel
 */
@Composable
internal fun MainNavHost(
    navigationContainer: NavigationContainer,
    navigationViewModel: NavigationViewModel = viewModel(
        factory = navigationContainer.navigationViewModelProvider,
    ),
    navHostController: NavHostController = rememberNavController(),
    authResultFlow: StateFlow<YandexAuthResult?>,
    onRequestOAuth: () -> Unit,
    onAuthSuccess: () -> Unit,
) {
    val authTokenState by navigationViewModel.authTokenState.collectAsStateWithLifecycle()

    if (authTokenState is AuthTokenState.Loading) {
        FullscreenLoader()
    } else {
        MainNavHost(
            navHostController = navHostController,
            startDestination = if (authTokenState is AuthTokenState.Found) NavigationScreen.List else NavigationScreen.Auth,
            authResultFlow = authResultFlow,
            onRequestOAuth = onRequestOAuth,
            onAuthSuccess = onAuthSuccess,
        )
    }
}

@Composable
private fun MainNavHost(
    navHostController: NavHostController,
    startDestination: NavigationScreen,
    authResultFlow: StateFlow<YandexAuthResult?>,
    onRequestOAuth: () -> Unit,
    onAuthSuccess: () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination.routeWithArguments,
        enterTransition = { enterTransition(navHostController = navHostController) },
        exitTransition = { exitTransition(navHostController = navHostController) },
    ) {
        authScreenComposable(
            navHostController = navHostController,
            onRequestOAuth = onRequestOAuth,
            onAuthSuccess = onAuthSuccess,
            authResultFlow = authResultFlow,
        )

        listScreenComposable(navHostController = navHostController)

        taskScreenComposable(navHostController = navHostController)

        settingsScreenComposable(navHostController = navHostController)
    }
}

private fun enterTransition(navHostController: NavHostController) =
    slideInHorizontally { fullWidth ->
        if (navHostController.currentDestination?.route == NavigationScreen.List.routeWithArguments) 0 else fullWidth
    }

private fun exitTransition(navHostController: NavHostController) =
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
