package ru.serg.widget_settings_feature.screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.serg.datastore.DataStoreDataSource
import javax.inject.Inject

@HiltViewModel
class WidgetSettingsViewModel @Inject constructor(
    private val dataSource: DataStoreDataSource
) : ViewModel() {
    private val _widgetColorFlow = MutableStateFlow(Color.White)
    val widgetColorFlow = _widgetColorFlow.asStateFlow()

    private val _widgetBigFontFlow = MutableStateFlow(0f)
    val widgetBigFontFlow = _widgetBigFontFlow.asStateFlow()

    private val _widgetSmallFontFlow = MutableStateFlow(0f)
    val widgetSmallFontFlow = _widgetSmallFontFlow.asStateFlow()

    private val _isWidgetSystemDataShown = MutableStateFlow(false)
    val isWidgetSystemDataShown = _isWidgetSystemDataShown.asStateFlow()

    init {
        initWidgetColor()
        initWidgetBigFontSize()
        initWidgetSmallFontSize()
        initWidgetSystemData()
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

    private fun initWidgetBigFontSize() {
        viewModelScope.launch {
            dataSource.widgetBigFontSize.collectLatest {
                println("Collecting $it")
                _widgetBigFontFlow.value = it.toFloat()
            }
        }
    }

    fun saveWidgetBigFont(size: Float) {
        viewModelScope.launch {
            dataSource.saveWidgetBigFontSize(size.toInt())
        }
    }

    private fun initWidgetSmallFontSize() {
        viewModelScope.launch {
            dataSource.widgetSmallFontSize.distinctUntilChanged().collectLatest {
                _widgetSmallFontFlow.value = it.toFloat()
            }
        }
    }

    fun saveWidgetSmallFont(size: Float) {
        viewModelScope.launch {
            dataSource.saveWidgetSmallFontSize(size.toInt())
        }
    }

    private fun initWidgetSystemData() {
        viewModelScope.launch {
            dataSource.isWidgetSystemDataShown.collectLatest { isShown ->
                _isWidgetSystemDataShown.update {
                    isShown
                }
            }
        }
    }

    fun saveWidgetSystemDataShown(isShown: Boolean) {
        viewModelScope.launch {
            dataSource.saveWidgetSystemDataShown(isShown)
        }
    }
}