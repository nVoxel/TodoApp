package com.voxeldev.todoapp.auth.ui

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.auth.extensions.setTranslucentBars
import com.voxeldev.todoapp.auth.extensions.setTransparentBars
import com.voxeldev.todoapp.auth.ui.components.AuthCard
import com.voxeldev.todoapp.auth.ui.components.AuthContrastButton
import com.voxeldev.todoapp.auth.ui.components.AuthTextField
import com.voxeldev.todoapp.auth.ui.preview.AuthScreenPreviewParameterProvider
import com.voxeldev.todoapp.auth.viewmodel.AuthViewModel
import com.voxeldev.todoapp.designsystem.components.TodoButton
import com.voxeldev.todoapp.designsystem.components.TodoDivider
import com.voxeldev.todoapp.designsystem.icons.AdditionalIcons
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

private const val SLIDE_DURATION_MILLIS = 400
private const val FADE_DURATION_MILLIS = 150

/**
 * @author nvoxel
 */
@Composable
fun AuthScreen(
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
        onOAuthMethodClicked = {
            reverseAnimation = false
            viewModel.onOAuthMethodClicked()
        },
        onOAuthMethodContinueClicked = {
            reverseAnimation = false
            viewModel.onOAuthMethodContinueClicked()
        },
        onCheckAuthClicked = viewModel::checkAuth,
        onUpdateLoginText = viewModel::onUpdateLoginText,
        onUpdatePasswordText = viewModel::onUpdatePasswordText,
        onUpdateTokenText = viewModel::onUpdateTokenText,
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
    onOAuthMethodContinueClicked: () -> Unit,
    onCheckAuthClicked: () -> Unit,
    onUpdateLoginText: (String) -> Unit,
    onUpdatePasswordText: (String) -> Unit,
    onUpdateTokenText: (String) -> Unit,
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

                            is AuthScreenState.OAuthMethodInfo -> {
                                OAuthMethodInfoCard(
                                    onContinueClicked = onOAuthMethodContinueClicked,
                                    onCloseClicked = onChooseMethodClicked,
                                )
                            }

                            is AuthScreenState.OAuthMethod -> {
                                OAuthMethodCard(
                                    tokenText = targetState.token,
                                    onUpdateTokenText = onUpdateTokenText,
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

@Composable
private fun ChooseMethodCard(
    onCredentialsMethodClicked: () -> Unit,
    onOAuthMethodClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    AuthCard {
        Text(
            text = stringResource(id = R.string.choose_method),
            color = appPalette.labelSecondary,
            style = AppTypography.body,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        TodoButton(
            modifier = Modifier
                .defaultMinSize(minHeight = 58.dp)
                .fillMaxWidth(),
            onClick = onCredentialsMethodClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = appPalette.colorGrayLight.copy(alpha = 0.5f),
                contentColor = appPalette.labelPrimary,
            ),
            shape = RoundedCornerShape(size = 24.dp),
        ) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.password_method),
                fontSize = 18.sp,
                style = AppTypography.button,
            )
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(width = 24.dp))

            TodoDivider(modifier = Modifier.weight(weight = 0.5f))

            Spacer(modifier = Modifier.width(width = 24.dp))

            Text(
                text = stringResource(id = R.string.methods_divider),
                color = appPalette.labelTertiary,
            )

            Spacer(modifier = Modifier.width(width = 24.dp))

            TodoDivider(modifier = Modifier.weight(weight = 0.5f))

            Spacer(modifier = Modifier.width(width = 24.dp))
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        AuthContrastButton(onClick = onOAuthMethodClicked) {
            Image(
                modifier = Modifier
                    .size(size = 24.dp)
                    .clip(shape = CircleShape),
                imageVector = AdditionalIcons.YandexLogo,
                contentDescription = stringResource(id = R.string.yandex_method),
            )

            Spacer(modifier = Modifier.width(width = 4.dp))

            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.yandex_method),
                fontSize = 18.sp,
                style = AppTypography.button,
            )
        }
    }
}

@Composable
private fun PasswordMethodCard(
    loginText: String,
    passwordText: String,
    onUpdateLoginText: (String) -> Unit,
    onUpdatePasswordText: (String) -> Unit,
    showLoading: Boolean,
    error: Throwable?,
    onRetryClicked: () -> Unit,
    onCheckAuthClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    AuthCard(
        showClose = true,
        onCloseClicked = onCloseClicked,
        showLoading = showLoading,
        error = error,
        retryCallback = onRetryClicked,
    ) { isForegroundVisible ->
        Text(
            text = stringResource(id = R.string.password_method_using),
            color = appPalette.labelSecondary,
            style = AppTypography.body,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            text = loginText,
            onTextChanged = onUpdateLoginText,
            placeholderText = stringResource(id = R.string.login),
            enabled = isForegroundVisible,
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            text = passwordText,
            onTextChanged = onUpdatePasswordText,
            placeholderText = stringResource(id = R.string.password),
            enabled = isForegroundVisible,
            secure = true,
            onDoneClicked = onCheckAuthClicked,
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AuthContrastButton(
            onClick = onCheckAuthClicked,
            enabled = isForegroundVisible,
        ) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.sign_in),
                fontSize = 18.sp,
                style = AppTypography.button,
            )
        }
    }
}

@Composable
private fun OAuthMethodInfoCard(
    onContinueClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    AuthCard(
        showClose = true,
        onCloseClicked = onCloseClicked,
    ) {
        Text(
            text = stringResource(id = R.string.yandex_method),
            color = appPalette.labelSecondary,
            style = AppTypography.body,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        Text(
            text = stringResource(id = R.string.yandex_method_info),
            textAlign = TextAlign.Center,
            color = appPalette.labelPrimary,
            style = AppTypography.body,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        AuthContrastButton(onClick = onContinueClicked) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.continue_label),
                fontSize = 18.sp,
                style = AppTypography.button,
            )
        }
    }
}

@Composable
private fun OAuthMethodCard(
    tokenText: String,
    onUpdateTokenText: (String) -> Unit,
    showLoading: Boolean,
    error: Throwable?,
    onRetryClicked: () -> Unit,
    onCheckAuthClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    AuthCard(
        showClose = true,
        onCloseClicked = onCloseClicked,
        showLoading = showLoading,
        error = error,
        retryCallback = onRetryClicked,
    ) { isForegroundVisible ->
        Text(
            text = stringResource(id = R.string.yandex_method_using),
            color = appPalette.labelSecondary,
            style = AppTypography.body,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            text = tokenText,
            onTextChanged = onUpdateTokenText,
            placeholderText = stringResource(id = R.string.token),
            enabled = isForegroundVisible,
            secure = true,
            onDoneClicked = onCheckAuthClicked,
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AuthContrastButton(
            onClick = onCheckAuthClicked,
            enabled = isForegroundVisible,
        ) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = stringResource(id = R.string.sign_in),
                fontSize = 18.sp,
                style = AppTypography.button,
            )
        }
    }
}

@Composable
private fun ManageSystemBars() {
    val window = (LocalContext.current as Activity).window
    val isSystemInDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(key1 = Unit) {
        window.setTransparentBars()
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            window.setTranslucentBars(
                isSystemInDarkTheme = isSystemInDarkTheme,
            )
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
            onOAuthMethodContinueClicked = {},
            onCheckAuthClicked = {},
            onUpdateLoginText = {},
            onUpdatePasswordText = {},
            onUpdateTokenText = {},
        )
    }
}
