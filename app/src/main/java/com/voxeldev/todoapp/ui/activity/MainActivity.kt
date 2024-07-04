package com.voxeldev.todoapp.ui.activity

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.voxeldev.todoapp.designsystem.theme.TodoAppTheme
import com.voxeldev.todoapp.ui.navigation.MainNavHost
import com.voxeldev.todoapp.ui.viewmodel.MainActivityViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author nvoxel
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private val loginOptions = YandexAuthLoginOptions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sdk = YandexAuthSdk.create(options = YandexAuthOptions(applicationContext))
        val launcher = registerForActivityResult(contract = sdk.contract) { result ->
            viewModel.onAuthResult(authResult = result)
        }

        setContent {
            enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(lightScrim = TRANSPARENT, darkScrim = TRANSPARENT),
            )

            TodoAppTheme {
                MainNavHost(
                    authResultFlow = viewModel.authResultFlow,
                    onRequestOAuth = { launcher.launch(input = loginOptions) },
                    onAuthSuccess = viewModel::onAuthSuccess,
                )
            }
        }
    }
}
