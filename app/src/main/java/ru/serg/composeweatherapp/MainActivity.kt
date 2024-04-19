package ru.serg.composeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.main_pager.main_screen.MainViewModel
import ru.serg.widgets.WeatherWidget

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        startMainScreen()
    }

    private fun startMainScreen() {
        val isDarkTheme = mutableStateOf(true)
        lifecycleScope.launch {
            viewModel.isDarkThemeEnabled.collectLatest {
                isDarkTheme.value = it
            }
        }

        lifecycleScope.launch {
            WeatherWidget().updateAll(applicationContext)
        }

        setContent {
            ComposeWeatherAppTheme(
                darkTheme = isDarkTheme
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(viewModel)
                }
            }
        }
    }
}


