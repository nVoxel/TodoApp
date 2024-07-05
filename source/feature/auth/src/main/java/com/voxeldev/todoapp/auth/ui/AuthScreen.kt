package com.voxeldev.todoapp.auth.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.auth.ui.components.ChooseMethodCard
import com.voxeldev.todoapp.auth.ui.components.ManageSystemBars
import com.voxeldev.todoapp.auth.ui.components.PasswordMethodCard
import com.voxeldev.todoapp.auth.ui.preview.AuthScreenPreviewParameterProvider
import com.voxeldev.todoapp.auth.viewmodel.AuthViewModel
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

private const val SLIDE_DURATION_MILLIS = 400
private const val FADE_DURATION_MILLIS = 150

/**
 * @author nvoxel
 */
@Composable
fun AuthScreen(
    onRequestOAuth: () -> Unit,
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel,
) {
    val authScreenState by viewModel.state.collectAsStateWithLifecycle()
    val showLoading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.exception.collectAsStateWithLifecycle()

    ManageSystemBars()

    // on close card
    var reverseAnimation by rememberSaveable { mutableStateOf(false) }

    if (authScreenState is AuthScreenState.Success) {
        onAuthSuccess()
    }

    AuthScreen(
        state = authScreenState,
        showLoading = showLoading,
        error = error,
        reverseAnimation = reverseAnimation,
        onRetryClicked = viewModel::onRetryClicked,
        onChooseMethodClicked = {
            reverseAnimation = true
            viewModel.onChooseMethodClicked()
        },
        onCredentialsMethodClicked = {
            reverseAnimation = false
            viewModel.onBearerMethodClicked()
        },
        onOAuthMethodClicked = onRequestOAuth,
        onCheckAuthClicked = viewModel::checkAuth,
        onUpdateLoginText = viewModel::onUpdateLoginText,
        onUpdatePasswordText = viewModel::onUpdatePasswordText,
    )
}

@Composable
private fun AuthScreen(
    state: AuthScreenState,
    showLoading: Boolean,
    error: Throwable?,
    reverseAnimation: Boolean,
    onRetryClicked: () -> Unit,
    onChooseMethodClicked: () -> Unit,
    onCredentialsMethodClicked: () -> Unit,
    onOAuthMethodClicked: () -> Unit,
    onCheckAuthClicked: () -> Unit,
    onUpdateLoginText: (String) -> Unit,
    onUpdatePasswordText: (String) -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.auth_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Scaffold(
            containerColor = appPalette.backTint,
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(all = 8.dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Column {
                    AnimatedContent(
                        targetState = state,
                        transitionSpec = {
                            getCardsTransition(reverseAnimation = reverseAnimation)
                        },
                        contentKey = { targetState -> targetState.stateKey },
                        contentAlignment = Alignment.BottomCenter,
                    ) { targetState ->
                        when (targetState) {
                            is AuthScreenState.ChooseMethod -> {
                                ChooseMethodCard(
                                    onCredentialsMethodClicked = onCredentialsMethodClicked,
                                    onOAuthMethodClicked = onOAuthMethodClicked,
                                    showLoading = showLoading,
                                    error = error,
                                    onRetryClicked = onRetryClicked,
                                )
                            }

                            is AuthScreenState.BearerMethod -> {
                                PasswordMethodCard(
                                    loginText = targetState.login,
                                    passwordText = targetState.password,
                                    onUpdateLoginText = onUpdateLoginText,
                                    onUpdatePasswordText = onUpdatePasswordText,
                                    showLoading = showLoading,
                                    error = error,
                                    onRetryClicked = onRetryClicked,
                                    onCheckAuthClicked = onCheckAuthClicked,
                                    onCloseClicked = onChooseMethodClicked,
                                )
                            }

                            else -> {}
                        }
                    }

                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.ime))
                }
            }
        }
    }
}

private fun getCardsTransition(reverseAnimation: Boolean) =
    slideInHorizontally(
        animationSpec = tween(durationMillis = SLIDE_DURATION_MILLIS),
        initialOffsetX = { fullWidth -> if (reverseAnimation) -fullWidth else fullWidth },
    ) togetherWith
            slideOutHorizontally(
                animationSpec = tween(durationMillis = SLIDE_DURATION_MILLIS),
                targetOffsetX = { fullWidth -> if (reverseAnimation) fullWidth else -fullWidth },
            ) + fadeOut(animationSpec = tween(durationMillis = FADE_DURATION_MILLIS))

@ScreenDayNightPreviews
@Composable
private fun AuthScreenPreview(
    @PreviewParameter(AuthScreenPreviewParameterProvider::class)
    authScreenState: AuthScreenState,
) {
    PreviewBase {
        AuthScreen(
            state = authScreenState,
            showLoading = false,
            error = null,
            reverseAnimation = false,
            onRetryClicked = {},
            onChooseMethodClicked = {},
            onCredentialsMethodClicked = {},
            onOAuthMethodClicked = {},
            onCheckAuthClicked = {},
            onUpdateLoginText = {},
            onUpdatePasswordText = {},
        )
    }
}
