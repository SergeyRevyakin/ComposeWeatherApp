package ru.serg.composeweatherapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.serg.main_pager.main_screen.MainScreen
import ru.serg.main_pager.main_screen.MainViewModel
import ru.serg.navigation.nav3.MainScreenNav
import ru.serg.navigation.nav3.SearchScreenNav
import ru.serg.navigation.nav3.SettingsScreenNav

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class, ExperimentalCoroutinesApi::class)
@Composable
fun EntryProviderScope<NavKey>.MainScreenNavigation(
    backStack: NavBackStack<NavKey>,
    viewModel: MainViewModel,
    animationDuration: Int
) =
    entry<MainScreenNav>(
        metadata = NavDisplay.transitionSpec {
            fadeIn(
                animationSpec = tween(animationDuration)
            ) togetherWith fadeOut(animationSpec = tween(animationDuration))
        }
    ) {
        val navigateToChooseCity = {
            backStack.add(SearchScreenNav)
            Unit
        }
        val navigateToSettings = {
            backStack.add(SettingsScreenNav)
            Unit
        }

        MainScreen(
            viewModel = viewModel,
            navigateToChooseCity = navigateToChooseCity,
            navigateToSettings = navigateToSettings,
        )
    }