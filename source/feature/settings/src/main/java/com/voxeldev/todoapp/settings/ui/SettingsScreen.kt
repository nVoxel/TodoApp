package com.voxeldev.todoapp.settings.ui

import android.graphics.Bitmap
import android.graphics.Picture
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.components.TodoSmallTopBar
import com.voxeldev.todoapp.designsystem.components.circularReveal
import com.voxeldev.todoapp.designsystem.components.clipShadow
import com.voxeldev.todoapp.designsystem.components.copyContentImage
import com.voxeldev.todoapp.designsystem.extensions.calculateTopBarElevation
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.settings.R
import com.voxeldev.todoapp.settings.di.SettingsScreenContainer
import com.voxeldev.todoapp.settings.ui.components.SettingsItem
import com.voxeldev.todoapp.settings.ui.components.SettingsThemeDialog
import com.voxeldev.todoapp.settings.ui.components.animateNewTheme
import com.voxeldev.todoapp.settings.viewmodel.SettingsViewModel
import com.voxeldev.todoapp.utils.extensions.copyToBitmap
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProviderDefaultImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal const val INITIAL_PROGRESS = 1f
internal const val TARGET_PROGRESS = 0f
internal const val TRANSITION_DURATION_MILLIS = 1000

private const val NO_VALUE = 0

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
            coroutineDispatcherProvider = settingsScreenContainer.coroutineDispatcherProvider,
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
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
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

    var transitionOverlayVisible by rememberSaveable { mutableStateOf(false) }
    val transitionProgress = remember { Animatable(initialValue = INITIAL_PROGRESS) }

    var width by remember { mutableStateOf(NO_VALUE) }
    var height by remember { mutableStateOf(NO_VALUE) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                width = layoutCoordinates.size.width
                height = layoutCoordinates.size.height
            },
    ) {
        if (width == NO_VALUE || height == NO_VALUE) return@Box

        val picture = remember { Picture() }
        val bitmap: Bitmap = remember {
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        Scaffold(
            modifier = Modifier
                .zIndex(zIndex = 1f)
                .circularReveal(transitionProgress.value)
                .copyContentImage(picture = picture),
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

                    coroutineScope.launch {
                        withContext(coroutineDispatcherProvider.ioDispatcher) {
                            picture.copyToBitmap(bitmap = bitmap)
                        }

                        transitionOverlayVisible = true

                        animateNewTheme(
                            selectedAppTheme = selectedAppTheme,
                            newAppTheme = newAppTheme,
                            isSystemInDarkTheme = isSystemInDarkTheme,
                            transitionProgress = transitionProgress,
                            onThemeChanged = onThemeChanged,
                        )

                        transitionOverlayVisible = false
                    }
                },
                isVisible = themeDialogVisible,
                onDismiss = { themeDialogVisible = false },
            )
        }

        if (transitionOverlayVisible) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
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

@ScreenDayNightPreviews
@Composable
private fun SettingsScreenPreview() {
    PreviewBase {
        SettingsScreen(
            coroutineDispatcherProvider = CoroutineDispatcherProviderDefaultImpl(),
            selectedAppTheme = AppTheme.Auto,
            onCloseClicked = {},
            onThemeChanged = {},
            onLogOutClicked = {},
        )
    }
}
