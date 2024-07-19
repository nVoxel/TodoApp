package com.voxeldev.todoapp.about.compose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.voxeldev.todoapp.about.di.AboutScreenContainer
import com.voxeldev.todoapp.about.div.AssetReader
import com.voxeldev.todoapp.about.div.Div2ViewFactory
import com.voxeldev.todoapp.about.div.TodoDivActionHandler
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.designsystem.theme.LocalAppTheme
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.expression.variables.DivVariableController
import com.yandex.div.data.Variable
import com.yandex.div.glide.GlideDivImageLoader
import java.util.Locale

private const val ABOUT_SCREEN_FILE = "about_screen.json"
private const val ABOUT_SCREEN_TEMPLATES = "templates"
private const val ABOUT_SCREEN_CARD = "card"

private const val VERSION_VAR = "version"

private const val APP_THEME_VAR = "app_theme"
private const val APP_THEME_LIGHT = "light"
private const val APP_THEME_DARK = "dark"

private const val LOCALE_VAR = "locale"
private const val LOCALE_RU = "ru"
private const val LOCALE_EN = "en"

private const val RU_ISO_CODE = "RU"

/**
 * @author nvoxel
 */
@Composable
fun AboutScreen(
    aboutScreenContainer: AboutScreenContainer,
    stringResourceProvider: StringResourceProvider = aboutScreenContainer.stringResourceProvider,
    onClose: () -> Unit,
) {
    val appPalette = LocalAppPalette.current
    val appTheme = LocalAppTheme.current
    val isDarkTheme = when (appTheme) {
        AppTheme.Auto -> isSystemInDarkTheme()
        AppTheme.Light -> false
        AppTheme.Dark -> true
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val assetReader = remember { AssetReader(context = context) }

    val divJson = remember { assetReader.read(filename = ABOUT_SCREEN_FILE) }
    val templatesJson = remember { divJson.optJSONObject(ABOUT_SCREEN_TEMPLATES) }
    val cardJson = remember { divJson.optJSONObject(ABOUT_SCREEN_CARD) }

    val actionHandler = remember {
        TodoDivActionHandler(
            onOpenProfile = {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(stringResourceProvider.getGitHubProfileUrl())),
                )
            },
            onOpenRepo = {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(stringResourceProvider.getGetHubRepoUrl())),
                )
            },
            onClose = onClose,
        )
    }

    val variableController = remember {
        DivVariableController().apply {
            putOrUpdate(
                Variable.StringVariable(
                    name = VERSION_VAR,
                    defaultValue = stringResourceProvider.getVersionName(),
                ),
            )

            putOrUpdate(
                Variable.StringVariable(
                    name = APP_THEME_VAR,
                    defaultValue = if (isDarkTheme) APP_THEME_DARK else APP_THEME_LIGHT,
                ),
            )

            putOrUpdate(
                Variable.StringVariable(
                    name = LOCALE_VAR,
                    defaultValue = if (Locale.getDefault().country == RU_ISO_CODE) LOCALE_RU else LOCALE_EN,
                ),
            )
        }
    }

    val divContext = remember {
        Div2Context(
            baseContext = context as Activity,
            configuration = createDivConfiguration(
                context = context,
                actionHandler = actionHandler,
                variableController = variableController,
            ),
            lifecycleOwner = lifecycleOwner,
        )
    }

    Scaffold(containerColor = appPalette.backPrimary) { paddingValues ->
        cardJson?.let {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                factory = {
                    Div2ViewFactory(
                        context = divContext,
                        templatesJson = templatesJson,
                    ).createView(cardJson = cardJson)
                },
            )
        }
    }
}

private fun createDivConfiguration(
    context: Context,
    actionHandler: DivActionHandler,
    variableController: DivVariableController,
): DivConfiguration {
    return DivConfiguration.Builder(GlideDivImageLoader(context = context))
        .actionHandler(actionHandler)
        .divVariableController(variableController)
        .visualErrorsEnabled(true)
        .build()
}
