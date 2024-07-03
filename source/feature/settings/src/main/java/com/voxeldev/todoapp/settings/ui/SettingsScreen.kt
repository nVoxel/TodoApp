package com.voxeldev.todoapp.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.components.TodoSmallTopBar
import com.voxeldev.todoapp.designsystem.components.clipShadow
import com.voxeldev.todoapp.designsystem.extensions.calculateTopBarElevation
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.screens.BaseScreen
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.settings.R
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
        },
        containerColor = appPalette.backPrimary,
    ) { paddingValues ->
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
}

@Composable
private fun SettingsItem(
    titleText: String,
    descriptionText: String,
    onClick: () -> Unit,
    iconVector: ImageVector,
    isDangerous: Boolean = false,
) {
    val appPalette = LocalAppPalette.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = titleText,
            tint = if (isDangerous) appPalette.colorRed else appPalette.labelPrimary,
        )

        Spacer(modifier = Modifier.width(width = 12.dp))

        Column {
            Text(
                text = titleText,
                color = if (isDangerous) appPalette.colorRed else appPalette.labelPrimary,
                style = AppTypography.body,
            )

            Spacer(modifier = Modifier.height(height = 4.dp))

            Text(
                text = descriptionText,
                color = if (isDangerous) appPalette.colorRedSecondary else appPalette.labelSecondary,
                style = AppTypography.body,
            )
        }
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
