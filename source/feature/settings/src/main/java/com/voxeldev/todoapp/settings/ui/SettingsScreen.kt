package com.voxeldev.todoapp.settings.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.components.TodoSmallTopBar
import com.voxeldev.todoapp.designsystem.components.clipShadow
import com.voxeldev.todoapp.designsystem.extensions.calculateTopBarElevation
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.settings.R
import com.voxeldev.todoapp.settings.ui.components.SettingsItem
import com.voxeldev.todoapp.settings.viewmodel.SettingsViewModel

/**
 * @author nvoxel
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onClose: () -> Unit,
    onLoggedOut: () -> Unit,
) {
    BaseScreen(
        viewModel = viewModel,
        retryCallback = viewModel::onRetryClicked,
    ) {
        SettingsScreen(
            onLogOutClicked = {
                viewModel.logOut(successCallback = onLoggedOut)
            },
            onCloseClicked = onClose,
        )
    }
}

@Composable
private fun SettingsScreen(
    onLogOutClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    val scrollState = rememberScrollState()

    Scaffold(
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
            onLogOutClicked = onLogOutClicked,
        )
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
    onLogOutClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .verticalScroll(state = scrollState),
    ) {
        SettingsItem(
            titleText = stringResource(id = R.string.auto_refresh_interval),
            descriptionText = stringResource(id = R.string.auto_refresh_interval_description),
            onClick = {},
            iconVector = Icons.Default.Timer,
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
            onLogOutClicked = {},
            onCloseClicked = {},
        )
    }
}
