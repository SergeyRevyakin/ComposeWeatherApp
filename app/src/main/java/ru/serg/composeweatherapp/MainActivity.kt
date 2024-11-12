package ru.serg.composeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.WindowCompat
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.main_pager.main_screen.MainViewModel
import ru.serg.widgets.WeatherWidget
import ru.serg.widgets.worker.UpdateWorker

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        startMainScreen()
        enableEdgeToEdge()
    }

    private fun startMainScreen() {
        val isDarkTheme = mutableStateOf(true)
        lifecycleScope.launch {
            viewModel.isDarkThemeEnabled.collectLatest {
                isDarkTheme.value = it
            }
        }

        lifecycleScope.launch {
            UpdateWorker.setupPeriodicWork(applicationContext)
            WeatherWidget().updateAll(applicationContext)
        }

        setContent {
            ComposeWeatherAppTheme(
                darkTheme = isDarkTheme
            ) {
                Surface {
                    Navigation(viewModel)
                }
            }
        }
    }
}


