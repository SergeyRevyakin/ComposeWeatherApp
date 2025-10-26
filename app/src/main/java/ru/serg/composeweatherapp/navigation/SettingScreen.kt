package ru.serg.composeweatherapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.FlowPreview
import ru.serg.navigation.nav3.SettingsScreenNav
import ru.serg.navigation.nav3.WidgetSettingsScreenNav
import ru.serg.settings_feature.screen.SettingsScreen

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun EntryProviderScope<NavKey>.SettingsScreenNavigation(
    backStack: NavBackStack<NavKey>,
    navigateBack: () -> Unit
) =
    entry<SettingsScreenNav> {
        SettingsScreen(
            navigateBack = navigateBack,
            navigateToWidgetSettings = {
                backStack.add(
                    WidgetSettingsScreenNav
                )
            }
        )
    }