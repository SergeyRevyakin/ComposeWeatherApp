package ru.serg.composeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.ui.Navigation
import ru.serg.datastore.DataStoreDataSource
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ru.serg.main_pager.main_screen.MainViewModel by viewModels()

    @Inject
    lateinit var dataStoreDataSource: DataStoreDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        startMainScreen()
    }

    private fun startMainScreen() {
        val isDarkTheme = mutableStateOf(true)
        lifecycleScope.launch {
            dataStoreDataSource.isDarkThemeEnabled.collectLatest {
                isDarkTheme.value = it
            }
        }

        setContent {
            ComposeWeatherAppTheme(
                darkTheme = isDarkTheme
            ) {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(viewModel)
                }
            }
        }
    }
}


