package ru.serg.composeweatherapp.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.serg.composeweatherapp.ui.theme.headerModifier

@Composable
fun SettingsScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "TEXT",
            modifier = Modifier.headerModifier()
        )
    }
}