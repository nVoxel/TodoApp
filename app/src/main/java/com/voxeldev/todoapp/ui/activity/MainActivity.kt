package com.voxeldev.todoapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
            TodoAppTheme {
                MainNavHost()
            }
        }
    }
}
