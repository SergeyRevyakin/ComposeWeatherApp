package ru.serg.widget_settings_feature.screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.datastore.DataStoreDataSource
import javax.inject.Inject

@HiltViewModel
class WidgetSettingsViewModel @Inject constructor(
    private val dataSource: DataStoreDataSource
) : ViewModel() {
    private val _widgetColorFlow = MutableStateFlow(Color.White)
    val widgetColorFlow = _widgetColorFlow.asStateFlow()

    init {
        initWidgetColor()
    }

    private fun initWidgetColor() {
        viewModelScope.launch {
            dataSource.widgetColorCode.collectLatest {
                _widgetColorFlow.value = Color(it.toULong())
            }
        }
    }

    fun saveWidgetColor(color: Color) {
        viewModelScope.launch {
            dataSource.saveWidgetColorCode(color.value.toLong())
        }
    }
}