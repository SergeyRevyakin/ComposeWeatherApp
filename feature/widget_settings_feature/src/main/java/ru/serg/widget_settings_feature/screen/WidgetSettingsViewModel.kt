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

    private val _isWidgetWeatherChangesShown = MutableStateFlow(false)
    val isWidgetWeatherChangesShown = _isWidgetWeatherChangesShown.asStateFlow()

    private val _widgetBottomPadding = MutableStateFlow(0f)
    val widgetBottomPadding = _widgetBottomPadding.asStateFlow()

    private val _widgetIconSize = MutableStateFlow(0f)
    val widgetIconSize = _widgetIconSize.asStateFlow()

    init {
        initValues()
    }

    private fun initValues() {
        initWidgetColor()
        initWidgetBigFontSize()
        initWidgetSmallFontSize()
        initWidgetSystemData()
        initWidgetIconSize()
        initWidgetBottomPadding()
        initWidgetWeatherChanges()
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

    private fun initWidgetIconSize() {
        viewModelScope.launch {
            dataSource.widgetIconSize.collectLatest { value ->
                _widgetIconSize.update {
                    value.toFloat()
                }
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

    private fun initWidgetWeatherChanges() {
        viewModelScope.launch {
            dataSource.isWidgetWeatherChangesShown.collectLatest { isShown ->
                _isWidgetWeatherChangesShown.update {
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

    fun saveWidgetWeatherChangesShown(isShown: Boolean) {
        viewModelScope.launch {
            dataSource.saveWidgetWeatherChangesShown(isShown)
        }
    }

    private fun initWidgetBottomPadding() {
        viewModelScope.launch {
            dataSource.widgetBottomPadding.collectLatest { padding ->
                _widgetBottomPadding.update {
                    padding.toFloat()
                }
            }
        }
    }

    fun saveWidgetBottomPadding(padding: Float) {
        viewModelScope.launch {
            dataSource.saveWidgetBottomPadding(padding.toInt())
        }
    }

    fun saveWidgetIconSize(iconSize: Float) {
        viewModelScope.launch {
            dataSource.saveWidgetIconSize(iconSize.toInt())
        }
    }
}