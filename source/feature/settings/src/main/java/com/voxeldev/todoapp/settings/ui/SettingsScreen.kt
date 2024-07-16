package com.voxeldev.todoapp.settings.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.components.TodoSmallTopBar
import com.voxeldev.todoapp.designsystem.components.circularReveal
import com.voxeldev.todoapp.designsystem.components.clipShadow
import com.voxeldev.todoapp.designsystem.extensions.calculateTopBarElevation
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.settings.R
import com.voxeldev.todoapp.settings.di.SettingsScreenContainer
import com.voxeldev.todoapp.settings.ui.components.SettingsItem
import com.voxeldev.todoapp.settings.ui.components.SettingsThemeDialog
import com.voxeldev.todoapp.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val INITIAL_PROGRESS = 1f
private const val TARGET_PROGRESS = 0f
private const val TRANSITION_DURATION_MILLIS = 500

/**
 * @author nvoxel
 */
@Composable
fun SettingsScreen(
    settingsScreenContainer: SettingsScreenContainer,
    viewModel: SettingsViewModel = viewModel(
        factory = settingsScreenContainer.settingsViewModelProvider,
    ),
    onClose: () -> Unit,
    onThemeChanged: (AppTheme) -> Unit,
    onLoggedOut: () -> Unit,
) {
    val selectedAppTheme by viewModel.appTheme.collectAsStateWithLifecycle()

    BaseScreen(
        viewModel = viewModel,
        retryCallback = viewModel::onRetryClicked,
    ) {
        SettingsScreen(
            selectedAppTheme = selectedAppTheme,
            onCloseClicked = onClose,
            onThemeChanged = { newAppTheme ->
                onThemeChanged(newAppTheme)
                viewModel.changeSelectedAppTheme(appTheme = newAppTheme)
            },
            onLogOutClicked = { viewModel.logOut(successCallback = onLoggedOut) },
        )
    }
}

@Composable
private fun SettingsScreen(
    selectedAppTheme: AppTheme,
    onCloseClicked: () -> Unit,
    onThemeChanged: (AppTheme) -> Unit,
    onLogOutClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val isSystemInDarkTheme = isSystemInDarkTheme()
    var themeDialogVisible by rememberSaveable { mutableStateOf(false) }

    val transitionProgress = remember { Animatable(initialValue = INITIAL_PROGRESS) }

    Surface(color = appPalette.colorBlue) {
        Scaffold(
            modifier = Modifier.circularReveal(transitionProgress.value),
            topBar = {
                SettingsScreenTopBar(
                    scrollState = scrollState,
                    onCloseClicked = onCloseClicked,
                )
            },
            containerColor = appPalette.backPrimary,
        ) { paddingValues ->
            SettingsScreenContent(
                paddingValues = paddingValues,
                scrollState = scrollState,
                onThemeClicked = { themeDialogVisible = true },
                onLogOutClicked = onLogOutClicked,
            )

            SettingsThemeDialog(
                selectedTheme = selectedAppTheme,
                onThemeSelected = { newAppTheme ->
                    themeDialogVisible = false
                    animateNewTheme(
                        coroutineScope = coroutineScope,
                        selectedAppTheme = selectedAppTheme,
                        newAppTheme = newAppTheme,
                        isSystemInDarkTheme = isSystemInDarkTheme,
                        transitionProgress = transitionProgress,
                        onThemeChanged = onThemeChanged,
                    )
                },
                isVisible = themeDialogVisible,
                onDismiss = { themeDialogVisible = false },
            )
        }
    }
}

@Composable
private fun SettingsScreenTopBar(
    scrollState: ScrollState,
    onCloseClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .clipShadow(),
        shadowElevation = scrollState.calculateTopBarElevation(),
    ) {
        TodoSmallTopBar(
            onCloseClicked = onCloseClicked,
            displayTitle = true,
            titleText = stringResource(id = R.string.settings),
        )
    }
}

@Composable
private fun SettingsScreenContent(
    paddingValues: PaddingValues,
    scrollState: ScrollState,
    onThemeClicked: () -> Unit,
    onLogOutClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .verticalScroll(state = scrollState),
    ) {
        SettingsItem(
            titleText = stringResource(id = R.string.app_theme),
            descriptionText = stringResource(id = R.string.app_theme_description),
            onClick = onThemeClicked,
            iconVector = Icons.Default.Palette,
        )

        TodoDivider(modifier = Modifier.padding(horizontal = 16.dp))

        SettingsItem(
            titleText = stringResource(id = R.string.log_out),
            descriptionText = stringResource(id = R.string.log_out_description),
            onClick = onLogOutClicked,
            iconVector = Icons.AutoMirrored.Default.Logout,
            isDangerous = true,
        )
    }
}

private fun animateNewTheme(
    coroutineScope: CoroutineScope,
    selectedAppTheme: AppTheme,
    newAppTheme: AppTheme,
    isSystemInDarkTheme: Boolean,
    transitionProgress: Animatable<Float, AnimationVector1D>,
    onThemeChanged: (AppTheme) -> Unit,
) {
    coroutineScope.launch {
        val shouldAnimateNewTheme = shouldAnimateNewTheme(
            previousTheme = selectedAppTheme,
            nextTheme = newAppTheme,
            isSystemInDarkTheme = isSystemInDarkTheme,
        )

        if (shouldAnimateNewTheme) {
            transitionProgress.animateTo(
                targetValue = TARGET_PROGRESS,
                animationSpec = tween(durationMillis = TRANSITION_DURATION_MILLIS),
            )
        }

        onThemeChanged(newAppTheme)

        if (shouldAnimateNewTheme) {
            transitionProgress.animateTo(
                targetValue = INITIAL_PROGRESS,
               animationSpec = tween(durationMillis = TRANSITION_DURATION_MILLIS),
            )
        }
    }
}

private fun shouldAnimateNewTheme(
    previousTheme: AppTheme,
    nextTheme: AppTheme,
    isSystemInDarkTheme: Boolean,
): Boolean {
    val actualPreviousTheme = if (previousTheme == AppTheme.Auto) {
        if (isSystemInDarkTheme) AppTheme.Dark else AppTheme.Light
    } else {
        previousTheme
    }

    val actualNextTheme = if (nextTheme == AppTheme.Auto) {
        if (isSystemInDarkTheme) AppTheme.Dark else AppTheme.Light
    } else {
        nextTheme
    }

    return actualPreviousTheme != actualNextTheme
}

@ScreenDayNightPreviews
@Composable
private fun SettingsScreenPreview() {
    PreviewBase {
        SettingsScreen(
            selectedAppTheme = AppTheme.Auto,
            onCloseClicked = {},
            onThemeChanged = {},
            onLogOutClicked = {},
        )
    }
}
