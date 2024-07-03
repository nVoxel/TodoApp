package com.voxeldev.todoapp.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.components.TodoSmallTopBar
import com.voxeldev.todoapp.designsystem.extensions.calculateTopBarElevation
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
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
            Surface(shadowElevation = scrollState.calculateTopBarElevation()) {
                TodoSmallTopBar(
                    onCloseClicked = onCloseClicked,
                    displayTitle = true,
                    titleText = "Settings",
                )
            }
        },
        containerColor = appPalette.backPrimary,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(state = scrollState),
        ) {
            SettingsItem(onClick = onLogOutClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Logout,
                    contentDescription = "Log Out",
                    tint = appPalette.colorRed,
                )

                Spacer(modifier = Modifier.width(width = 12.dp))

                Text(
                    text = "Log Out",
                    color = appPalette.colorRed,
                    style = AppTypography.body,
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
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
