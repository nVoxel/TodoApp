package com.voxeldev.todoapp.ui.activity

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.voxeldev.todoapp.designsystem.theme.TodoAppTheme
import com.voxeldev.todoapp.di.main.DaggerMainActivityComponent
import com.voxeldev.todoapp.di.main.MainActivityComponent
import com.voxeldev.todoapp.domain.usecase.preferences.GetAutoRefreshIntervalUseCase
import com.voxeldev.todoapp.settings.work.setupAutoRefreshWork
import com.voxeldev.todoapp.ui.navigation.MainNavHost
import com.voxeldev.todoapp.ui.navigation.rememberNavigationContainer
import com.voxeldev.todoapp.ui.viewmodel.MainActivityViewModel
import com.voxeldev.todoapp.utils.extensions.lazyUnsafe
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import javax.inject.Inject

/**
 * Main app activity.
 * @author nvoxel
 */
class MainActivity : ComponentActivity() {

    private val mainActivityComponent: MainActivityComponent by lazyUnsafe {
        DaggerMainActivityComponent.factory().create(applicationContext = applicationContext)
    }

    private val viewModel: MainActivityViewModel by viewModels()

    private val loginOptions = YandexAuthLoginOptions()

    @Inject
    lateinit var getAutoRefreshIntervalUseCase: GetAutoRefreshIntervalUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityComponent.inject(this)

        setupAutoRefreshWork(
            getAutoRefreshIntervalUseCase = getAutoRefreshIntervalUseCase,
            scope = lifecycleScope,
        )

        val sdk = YandexAuthSdk.create(options = YandexAuthOptions(context = applicationContext))
        val launcher = registerForActivityResult(contract = sdk.contract) { result ->
            viewModel.onAuthResult(authResult = result)
        }

        setContent {
            enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(lightScrim = TRANSPARENT, darkScrim = TRANSPARENT),
            )

            TodoAppTheme {
                MainNavHost(
                    navigationContainer = rememberNavigationContainer(),
                    authResultFlow = viewModel.authResultFlow,
                    onRequestOAuth = { launcher.launch(input = loginOptions) },
                    onAuthSuccess = viewModel::onAuthSuccess,
                )
            }
        }
    }
}
