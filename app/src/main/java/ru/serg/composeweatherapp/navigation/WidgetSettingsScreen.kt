package ru.serg.composeweatherapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import kotlinx.coroutines.FlowPreview
import ru.serg.navigation.nav3.WidgetSettingsScreenNav
import ru.serg.widget_settings_feature.screen.WidgetSettingsScreen

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun EntryProviderBuilder<*>.WidgetSettingsScreenNavigation(navigateBack: () -> Unit) =
    entry<WidgetSettingsScreenNav> {
        WidgetSettingsScreen(
            navigateBack = navigateBack
        )
    }