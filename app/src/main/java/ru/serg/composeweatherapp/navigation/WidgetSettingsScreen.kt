package ru.serg.composeweatherapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.FlowPreview
import ru.serg.navigation.nav3.WidgetSettingsScreenNav
import ru.serg.widget_settings_feature.screen.WidgetSettingsScreen

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun EntryProviderScope<NavKey>.WidgetSettingsScreenNavigation(navigateBack: () -> Unit) =
    entry<WidgetSettingsScreenNav> {
        WidgetSettingsScreen(
            navigateBack = navigateBack
        )
    }