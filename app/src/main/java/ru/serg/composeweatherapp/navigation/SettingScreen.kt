package ru.serg.composeweatherapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import kotlinx.coroutines.FlowPreview
import ru.serg.navigation.nav3.SettingsScreenNav
import ru.serg.navigation.nav3.WidgetSettingsScreenNav
import ru.serg.settings_feature.screen.SettingsScreen

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun EntryProviderBuilder<*>.SettingsScreenNavigation(
    backStack: SnapshotStateList<NavKey>,
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