package com.voxeldev.todoapp.ui.activity

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.voxeldev.todoapp.designsystem.theme.TodoAppTheme
import com.voxeldev.todoapp.ui.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author nvoxel
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(lightScrim = TRANSPARENT, darkScrim = TRANSPARENT),
            )

            TodoAppTheme {
                MainNavHost()
            }
        }
    }
}
