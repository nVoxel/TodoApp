package com.voxeldev.todoapp.auth.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.voxeldev.todoapp.auth.ui.AuthScreenState

/**
 * @author nvoxel
 */
internal class AuthScreenPreviewParameterProvider : PreviewParameterProvider<AuthScreenState> {
    override val values: Sequence<AuthScreenState> = sequenceOf(
        AuthScreenState.ChooseMethod,
        AuthScreenState.BearerMethod(
            login = "",
            password = "",
        ),
        AuthScreenState.BearerMethod(
            login = "nVoxel",
            password = "mypassword",
        ),
        AuthScreenState.OAuthMethodInfo,
        AuthScreenState.OAuthMethod(token = ""),
        AuthScreenState.OAuthMethod(token = "very_very_very_very_very_long_token"),
    )
}
